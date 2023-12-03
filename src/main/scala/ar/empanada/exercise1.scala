package ar.empanada

import ar.empanada.app.AdventOfCodeApp
import cats.effect.IO

object exercise1 extends AdventOfCodeApp {

  override val inputSolution1: String = "./src/main/resources/input1-1.txt"
  override val inputSolution2: String = "./src/main/resources/input1-2.txt"
  override def solution1: fs2.Stream[IO, String] => fs2.Stream[IO, Int] =
    line => line.map(captureFirstAndLastNumber).fold(0)((acc, n) => acc + n)

  override def solution2: fs2.Stream[IO, String] => fs2.Stream[IO, Int] =
    line => line.map(completeSolution2).fold(0)((acc, n) => acc + n)

  val numbers = Map(
    1 -> "one",
    2 -> "two",
    3 -> "three",
    4 -> "four",
    5 -> "five",
    6 -> "six",
    7 -> "seven",
    8 -> "eight",
    9 -> "nine"
  )

  def completeSolution2 = captureStringNumbers andThen captureFirstAndLastNumber

  def captureFirstAndLastNumber: String => Int = line => {
    val numbersInLine = line.filter(_.isDigit)
    val first = numbersInLine.head.toString.toInt
    if (numbersInLine.size == 1) buildNumber(first, first)
    else {
      val last = numbersInLine.last.toString.toInt
      buildNumber(first, last)
    }
  }


  def captureStringNumbers: String => String = line => {
    def recCaptureStringNumbers(
        str: List[Char],
        candidates: Map[Char, List[Position]] = initializeCandidates,
        acc: List[Int] = List.empty
    ): String = {
      str match {
        case Nil => acc.map(_.toString).mkString
        case ::(head, tail) if head.isDigit =>
          recCaptureStringNumbers(tail, acc = head.toString.toInt :: acc)
        case head :: tail =>
          val maybeCandidates = candidates.getOrElse(head, List.empty)
          val hasCompleted = maybeCandidates.filter(_.isCompleted)
          val values = candidates.values.flatten.toList
          if (maybeCandidates.isEmpty) recCaptureStringNumbers(tail, acc = acc)
          else if (hasCompleted.nonEmpty)
            recCaptureStringNumbers(
              tail,
              candidatesNextGen(maybeCandidates, values),
              acc = hasCompleted.head.number :: acc
            )
          else {
            recCaptureStringNumbers(tail, candidatesNextGen(maybeCandidates, values), acc)
          }
      }
    }
    recCaptureStringNumbers(line.toList).reverse
  }

  def initializeCandidates: Map[Char, List[Position]] =
    numbers.groupBy { case (k, v) => v.charAt(0) }.map { case (k, v) =>
      k -> v.keys.toList.map(n => Position(0, n))
    }

  def candidatesNextGen(
      maybeCandidates: List[Position],
      currentPositions: List[Position]
  ): Map[Char, List[Position]] = {
    val current: Map[Char, List[Position]] = currentPositions
      .filterNot(pos => maybeCandidates.contains(pos))
      .map { pos => numbers(pos.number).charAt(pos.charIndex) -> pos }
      .groupBy { case (k, v) => k }
      .map { case (k, v) => k -> v.map(_._2) }
    val iterated: Map[Char, List[Position]] = maybeCandidates
      .filter(!_.isCompleted)
      .map { can =>
        val nextCan = can.nextPosition
        numbers(nextCan.number).charAt(nextCan.charIndex) -> nextCan
      }
      .groupBy(_._1)
      .map { case (k, v) => k -> v.map(n => n._2) }

    val keyCandidates = iterated.keySet ++ current.keySet
    val filteredInit = initializeCandidates.filterNot { case (k, v) =>
      keyCandidates.contains(k)
    }
    mergeMap(mergeMap(iterated, current), filteredInit)
  }
  def buildNumber(first: Int, second: Int): Int = {
    s"$first$second".toInt
  }

  final case class Position(charIndex: Int, number: Int) {
    require(numbers.contains(number))

    /*Return None if completed*/
    def nextPosition: Position = {
      val strN = numbers(number)
      val nextIndex = charIndex + 1
      Position(nextIndex, number)
    }

    def isCompleted: Boolean = {
      val strN = numbers(number)
      strN.size <= nextPosition.charIndex
    }

  }
  def mergeMap(
      map1: Map[Char, List[Position]],
      map2: Map[Char, List[Position]]
  ): Map[Char, List[Position]] = {
    map1.keySet.foldLeft(map2){ case (acc, char) =>
      if (acc.contains(char)) {
        val merged = map1(char) ::: acc(char)
        acc + (char -> merged)
      } else {
        acc + (char -> map1(char))
      }
    }
  }

}
