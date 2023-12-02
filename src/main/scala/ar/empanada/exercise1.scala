package ar.empanada

import ar.empanada.app.AdventOfCodeApp
import cats.effect.IO

object exercise1 extends AdventOfCodeApp {
  override val inputSolution1: String = "./src/main/resources/input1-1.txt"
  override val inputSolution2: String = "./src/main/resources/input1-2.txt"

  override def solution1: fs2.Stream[IO, String] => fs2.Stream[IO, Int] =
    line => line.map(importantFn).fold(0)( (acc, n) => acc + n)

  def importantFn: String => Int = line => {
    val numbersInLine = line.filter(_.isDigit)
    val first = numbersInLine.head.toString.toInt
    if (numbersInLine.size == 1) buildNumber(first, first)
    else{
      val last = numbersInLine.last.toString.toInt
      buildNumber(first, last)
    }
  }

  def buildNumber(first: Int, second: Int): Int = {
    s"$first$second".toInt
  }

  override def solution2: fs2.Stream[IO, String] => fs2.Stream[IO, Int] =
    _ => fs2.Stream.emit(2)

}
