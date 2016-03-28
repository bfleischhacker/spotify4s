package org.spotify4s.util

import cats.std.string._
import cats.syntax.eq._
import cats.syntax.option._
import io.circe.Decoder

trait Enumerated[T <: Identifiable] {
  val all: Seq[T]

  private lazy val cachedEnumStr: String = all.map(_.identifier).mkString(",")

  implicit val decoder: Decoder[T] = Decoder.decodeString
    .emap(id => all.find(_.identifier === id)
      .toRightXor(s"$id doesn't match any of of the possible enumerations: $cachedEnumStr"))
}
