package org.spotify4s.models

import cats.data.Xor
import cats.syntax.xor._
import io.circe.{Decoder, Encoder}

import scala.util.matching.Regex


/**
  * An HTML link that opens a track, album, app, playlist or other Spotify
  * resource in a Spotify client (which client is determined by the user's
  * device and account settings at play.spotify.com).
  *
  * @param spotifyUrl
  **/
case class SpotifyUrl(spotifyUrl: String)

object SpotifyUrl {
  private val pattern: Regex = "^htt[p|ps]://open.spotify.com/(track|user|album|artist)/([a-zA-Z0-9\\-\\.\\_]+)$".r

  implicit val encoder: Encoder[SpotifyUrl] = Encoder.encodeString.contramap(_.spotifyUrl)

  implicit val decoder: Decoder[SpotifyUrl] = Decoder.decodeString
    .emap(str => verified(str)
      .fold[Xor[String, SpotifyUrl]](s"invalid SpotifyUrl format: $str doesn't match regex ${pattern.regex}".left)(_
      .right))


  def verified(spotifyUrl: String): Option[SpotifyUrl] = {
    pattern.findFirstMatchIn(spotifyUrl).map(_ => SpotifyUrl(spotifyUrl))
  }

}
