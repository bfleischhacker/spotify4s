package org.spotify4s.models

import cats.data.Xor
import cats.syntax.xor._
import io.circe.{Decoder, Encoder}

import scala.util.matching.Regex


/**
  * The unique string identifying the Spotify category that you can find at the
  * end of the Spotify URI for the category.
  *
  * @param spotifyCategoryId
  **/
case class SpotifyCategoryId(spotifyCategoryId: String)

object SpotifyCategoryId {
  private val pattern: Regex = "^[A-Za-z0-9]+$".r

  implicit val encoder: Encoder[SpotifyCategoryId] = Encoder.encodeString.contramap(_.spotifyCategoryId)

  implicit val decoder: Decoder[SpotifyCategoryId] = Decoder.decodeString
    .emap(str => verified(str)
      .fold[Xor[String, SpotifyCategoryId]](s"invalid SpotifyCategoryId format: $str doesn't match regex ${
      pattern
        .regex
    }".left)(_.right))


  def verified(spotifyCategoryId: String): Option[SpotifyCategoryId] = {
    pattern.findFirstMatchIn(spotifyCategoryId).map(_ => SpotifyCategoryId(spotifyCategoryId))
  }

}
