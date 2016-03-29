package org.spotify4s.models

import io.circe.{Decoder, Encoder, Json}


/**
  * @param after The cursor to use as key to find the next page of items.
  **/
case class Cursor(after: String)

object Cursor {
  implicit val decoder: Decoder[Cursor] = (Decoder.instance(_.get[String]("after"))).map(Cursor.apply)

  implicit val encoder: Encoder[Cursor] = Encoder
    .instance(cursor => Json.obj("after" -> Encoder[String].apply(cursor.after)))


}
