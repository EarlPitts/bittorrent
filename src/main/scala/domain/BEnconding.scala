package domain.BEncoding

import cats.*
import cats.implicits.*
import parsley.Parsley
import parsley.Parsley.*
import parsley.cats.instances.*
import parsley.character.*

import BEnconding.*

type KeyValue = (String, BEnconding)

enum BEnconding:
  case BInteger(value: Int)
  case BString(value: String)
  case BDict(value: Map[String, BEnconding])
  case BList(value: List[BEnconding])

val pInt: Parsley[Int] =
  some(digit).map(_.mkString.toInt)

val pString: Parsley[String] = for
  length <- pInt
  _ <- char(':')
  // str <- exactly(length, item).map(_.mkString)
  // TODO exactly/replicateA should be stack-safe
  str <- List.fill(length)(item).sequence.map(_.mkString)
yield str

val bInt: Parsley[BEnconding] =
  char('i') *> pInt.map(BInteger.apply) <* char('e')

val pPair: Parsley[KeyValue] = for
  key <- pString
  value <- p
yield (key, value)

val pDict: Parsley[BEnconding] = for
  _ <- char('d')
  dict <- some(pPair).map(Map.from).map(BDict.apply)
  _ <- char('e')
yield dict

val pList: Parsley[BEnconding] =
  char('l') *> some(p).map(BList.apply) <* char('e')

val p: Parsley[BEnconding] =
  bInt <|> (pString.map(BString.apply)) <|> pDict <|> pList
