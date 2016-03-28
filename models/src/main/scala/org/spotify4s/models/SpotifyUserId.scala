package org.spotify4s.models

import cats.data.Xor
import cats.syntax.xor._
import io.circe.{Decoder, Encoder}

import scala.util.matching.Regex


/**
  * The unique string identifying the Spotify user that you can find at the
  * end of the Spotify URI for the user. The ID of the current user can be
  * obtained via the Web API endpoint https://api.spotify.com/v1/me.
  *
  * @param spotifyUserId
  **/
case class SpotifyUserId(spotifyUserId: String)

object SpotifyUserId {
  private val pattern: Regex = "^[a-zA-Z0-9\\-\\.\\_]+$".r

  implicit val encoder: Encoder[SpotifyUserId] = Encoder.encodeString.contramap(_.spotifyUserId)

  implicit val decoder: Decoder[SpotifyUserId] = Decoder.decodeString
    .emap(str => verified(str)
      .fold[Xor[String, SpotifyUserId]](s"invalid SpotifyUserId format: $str doesn't match regex ${pattern.regex}"
      .left)(_.right))


  def verified(spotifyUserId: String): Option[SpotifyUserId] = {
    pattern.findFirstMatchIn(spotifyUserId).map(_ => SpotifyUserId(spotifyUserId))
  }

}
