package ar.empanada

import ar.empanada.exercise1.Position
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec

class exercise1Test extends AsyncWordSpec with Matchers {

  "add up two maps" in {
    val map1 = Map(
      'o' -> List(Position(0, 1))
    )
    val map2 = Map(
      'o' -> List(Position(1,2))
    )
    val expected = Map(
      'o' -> List(Position(0,1), Position(1,2))
    )
    exercise1.mergeMap(map1, map2) shouldBe expected
  }

  "capture string numbers"in {
    def assertInputOutput(input: String, output: String) =
      exercise1.captureStringNumbers(input) shouldBe output

    assertInputOutput("two4one", "241")
    assertInputOutput("eighthreeone", "831")

  }
  "is completed ok " in {
    Position(3, 1).isCompleted shouldBe true
    Position(1, 1).isCompleted shouldBe false
    Position(5, 5).isCompleted shouldBe true
    Position(4, 5).isCompleted shouldBe true
  }
  "get number ok" in {
    val input = "a1bcjiojca9ncc"
    exercise1.captureFirstAndLastNumber(input) shouldBe 19
  }

  "intialize candiates" in {
    exercise1.initializeCandidates shouldBe Map(
      'o' -> List(Position(0, 1)),
      't' -> List(Position(0, 2), Position(0, 3)),
      'f' -> List(Position(0, 5), Position(0, 4)),
      's' -> List(Position(0, 6), Position(0, 7)),
      'e' -> List(Position(0, 8)),
      'n' -> List(Position(0, 9))
    )
  }

  "get str number ok" in {
    def assertInputOutput(input: String, output: Int) =
      exercise1.completeSolution2(input) shouldBe (output)
    val input1 = "two1nine"
    val input2 = "eightwothree"
    val input3 = "1one39tn4"
    val output1 = 29
    val output2 = 83
    val output3 = 14

    assertInputOutput(input1, output1)
    assertInputOutput(input2, output2)
    assertInputOutput(input3, output3)

  }
}
