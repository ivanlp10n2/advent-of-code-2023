package ar.empanada

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec

class exercise1Test extends AsyncWordSpec with Matchers{

  "get number ok" in {
    val input = "a1bcjiojca9ncc"
    exercise1.importantFn(input) shouldBe 19
  }
}
