package ar.empanada

import ar.empanada.app.AdventOfCodeApp
import cats.effect.IO

object exercise2 extends AdventOfCodeApp{
  override val inputSolution1: String = "./src/main/resources/input1-1.txt"
  override val inputSolution2: String = ???

  override def solution1: fs2.Stream[IO, String] => fs2.Stream[IO, Int] = line => {
    fs2.Stream.emit(3)
  }

  override def solution2: fs2.Stream[IO, String] => fs2.Stream[IO, Int] = ???
}
