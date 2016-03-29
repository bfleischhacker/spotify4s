package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}

case class Page[T](href: String,
                   items: List[T],
                   limit: Int,
                   total: Int,
                   offset: Int,
                   next: Option[String],
                   previous: Option[String])

object Page {
  implicit def decoder[T: Decoder]: Decoder[Page[T]] = (
    Decoder.instance(_.get[String]("href")) |@|
      Decoder.instance(_.get[List[T]]("items")) |@|
      Decoder.instance(_.get[Int]("limit")) |@|
      Decoder.instance(_.get[Int]("total")) |@|
      Decoder.instance(_.get[Int]("offset")) |@|
      Decoder.instance(_.get[Option[String]]("next")) |@|
      Decoder.instance(_.get[Option[String]]("previous"))
    ).map(Page.apply)

  implicit def encoder[T: Encoder]: Encoder[Page[T]] = Encoder.instance(page => Json.obj(
    "href" -> Json.string(page.href),
    "items" -> Encoder[List[T]].apply(page.items),
    "limit" -> Json.int(page.limit),
    "total" -> Json.int(page.total),
    "offset" -> Json.int(page.offset),
    "next" -> Encoder[Option[String]].apply(page.next),
    "previous" -> Encoder[Option[String]].apply(page.previous)
  ))
}