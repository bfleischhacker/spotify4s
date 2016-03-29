package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}


/**
  * @param status  The HTTP status code (also returned in the response header; see [Response Status Codes](https://developer.spotify.com/web-api/user- guide/#response-status-codes) for more information).
  * @param message A short description of the cause of the error.
  **/
case class Error(status: Int, message: String)

object Error {
  implicit val decoder: Decoder[Error] = (Decoder.instance(_.get[Int]("status")) |@| Decoder
    .instance(_.get[String]("message"))).map(Error.apply)

  implicit val encoder: Encoder[Error] = Encoder
    .instance(error => Json
      .obj("status" -> Encoder[Int].apply(error.status), "message" -> Encoder[String].apply(error.message)))


}
