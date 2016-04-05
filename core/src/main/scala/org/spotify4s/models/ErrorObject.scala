package org.spotify4s.models

import io.circe.syntax._
import io.circe.{Decoder, Encoder, Json}

/**
  * Simple representation of the top level json object returned as part of an api error
  *
  * @param error the actually [[Error]] payload
  */
case class ErrorObject(error: Error)

object ErrorObject {
  implicit val decoder: Decoder[ErrorObject] = Decoder.instance(_.get[Error]("error")).map(ErrorObject.apply)

  implicit val encoder: Encoder[ErrorObject] = Encoder
    .instance(errorObject => Json.obj("error" -> errorObject.error.asJson))
}
