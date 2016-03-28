package org.spotify4s.models

import cats.data.Xor
import cats.syntax.xor._
import io.circe.{Decoder, Encoder}

import scala.util.matching.Regex


/**
  * The base-62 identifier that you can find at the end of the Spotify URI (see
  * Spotify URI) for an artist, track, album, playlist, etc. Unlike a Spotify
  * URI, a Spotify ID does not clearly identify the type of resource; that
  * information is provided elsewhere in the call.
  *
  * @param spotifyId
  **/
case class SpotifyId(spotifyId: String)

object SpotifyId {
  private val pattern: Regex = "^[A-Za-z0-9]+$".r

  implicit val encoder: Encoder[SpotifyId] = Encoder.encodeString.contramap(_.spotifyId)

  implicit val decoder: Decoder[SpotifyId] = Decoder.decodeString
    .emap(str => verified(str)
      .fold[Xor[String, SpotifyId]](s"invalid SpotifyId format: $str doesn't match regex ${pattern.regex}".left)(_
      .right))


  def verified(spotifyId: String): Option[SpotifyId] = {
    pattern.findFirstMatchIn(spotifyId).map(_ => SpotifyId(spotifyId))
  }

}
