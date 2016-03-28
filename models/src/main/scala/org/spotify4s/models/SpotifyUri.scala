package org.spotify4s.models

import cats.data.Xor
import cats.syntax.xor._
import io.circe.{Decoder, Encoder}

import scala.util.matching.Regex


/**
  * The resource identifier that you can enter, for example, in the Spotify
  * Desktop client's search box to locate an artist, album, or track. To find
  * a Spotify URI simply right-click (on Windows) or Ctrl-Click (on a Mac) on
  * the artist's or album's or track's name
  *
  * @param spotifyUri
  **/
case class SpotifyUri(spotifyUri: String)

object SpotifyUri {
  private val pattern: Regex = "^(spotify:)(((track|album|artist|user):[a-zA-Z0-9\\-\\.\\_]+)|(user:[a-zA-Z0-9\\-\\.\\_]+:playlist:[a-zA-Z0-9\\-\\.\\_]+))$"
    .r

  implicit val encoder: Encoder[SpotifyUri] = Encoder.encodeString.contramap(_.spotifyUri)

  implicit val decoder: Decoder[SpotifyUri] = Decoder.decodeString
    .emap(str => verified(str)
      .fold[Xor[String, SpotifyUri]](s"invalid SpotifyUri format: $str doesn't match regex ${pattern.regex}".left)(_
      .right))


  def verified(spotifyUri: String): Option[SpotifyUri] = {
    pattern.findFirstMatchIn(spotifyUri).map(_ => SpotifyUri(spotifyUri))
  }

}
