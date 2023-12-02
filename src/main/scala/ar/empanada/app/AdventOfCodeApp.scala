package ar.empanada.app

import cats.effect.{ExitCode, IO, IOApp}
import fs2.io.file.{Files, Path}

trait AdventOfCodeApp extends IOApp {

  val inputSolution1: String
  val inputSolution2: String

  def solution1: fs2.Stream[IO, String] => fs2.Stream[IO, Int]
  def solution2: fs2.Stream[IO, String] => fs2.Stream[IO, Int]

  def result1: IO[Int] = readAndProcess(Solution1).compile.onlyOrError
  def result2: IO[Int] = readAndProcess(Solution2).compile.onlyOrError

  private def readInput(pathStr: String): fs2.Stream[IO, String] = {
    val path = Path.apply(pathStr)
    Files
      .apply[IO]
      .readAll(path)
      .through(fs2.text.utf8.decode)
      .through(fs2.text.lines)
  }

  def readAndProcess(solution: Solution): fs2.Stream[IO, Int] = solution match {
    case Solution1 => readInput(inputSolution1).through(solution1)
    case Solution2 => readInput(inputSolution2).through(solution2)
  }
  override def run(args: List[String]): IO[ExitCode] = {
    readAndProcess(Solution1).compile.onlyOrError
      .flatTap(n => IO.println(Solution1.msg(n)))
      .as(ExitCode.Success)
  }

  sealed trait Solution {
    def msg(n: Int) = this match {
      case Solution1 => s"Result of Solution1 is : [$n]"
      case Solution2 => s"Result of Solution3 is : [$n]"
    }
  }
  final case object Solution1 extends Solution
  final case object Solution2 extends Solution
}
