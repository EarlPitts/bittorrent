package domain.BEncoding

import weaver.*
import weaver.scalacheck.*
import org.scalacheck.*

import BEnconding.*

val example = "i13420e"
val example2 = "d3:cow3:moo4:spam4:eggse"
val example3 = "d4:spaml1:a1:bee"

val bIntGen: Gen[String] =
  Gen.choose(0, Integer.MAX_VALUE).map(n => s"i${n}e")

val bStringGen: Gen[String] =
  Gen.alphaStr.map(s => s"${s.size}:${s}")

object BEncodingSuite extends SimpleIOSuite with Checkers:

  pureTest("Basic parsing"):
    val expected1 = BInteger(13420)
    val expected2 = BDict(
      Map(
        "cow" -> BString("moo"),
        "spam" -> BString("eggs")
      )
    )
    val expected3 =
      BDict(Map("spam" -> BList(List(BString("a"), BString("b")))))

    expect.all(
      p.parse(example).get == expected1,
      p.parse(example2).get == expected2,
      p.parse(example3).get == expected3
    )

  test("int parsing"):
    forall(bIntGen) { str =>
      expect(p.parse(str).isSuccess)
    }

  test("string parsing"):
    forall(bStringGen) { str =>
      expect(p.parse(str).isSuccess)
    }

  test("list parsing"):
    forall(bStringGen) { str =>
      expect(p.parse(str).isSuccess)
    }
