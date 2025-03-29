package domain.BEncoding

import cats.effect.*
import cats.implicits.*
import org.scalacheck.*
import weaver.*
import weaver.scalacheck.*

import java.nio.file.Files
import java.nio.file.Paths

import BEnconding.*

object Parsing extends SimpleIOSuite:
  val files =
    List
      .range(1, 6)
      .traverse { n =>
        IO.blocking {
          Files
            .readAllBytes(Paths.get(s"src/test/resources/$n.torrent"))
            .map(_.toChar)
            .mkString
            .trim
        }
      }

  test("parsing"):
    for
        fs <- files
        results = fs.map(p.parse(_))
    yield expect(results.forall(_.isSuccess))
