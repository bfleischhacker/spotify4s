package org.spotify4s.models

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
  private val pattern: Regex = "^htt[p|ps]://open.spotify.com/(track|user|album|artist)/([a-zA-Z0-9]+)$".r


  def verified(spotifyUrl: String): Option[SpotifyUrl] = {
    pattern.findFirstMatchIn(spotifyUrl).map(_ => SpotifyUrl(spotifyUrl))
  }

}
