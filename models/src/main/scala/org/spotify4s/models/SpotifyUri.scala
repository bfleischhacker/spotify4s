package org.spotify4s.models

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
  private val pattern: Regex = "^(spotify:)(track|album|artist):[a-zA-Z0-9]+$".r


  def verified(spotifyUri: String): Option[SpotifyUri] = {
    pattern.findFirstMatchIn(spotifyUri).map(_ => SpotifyUri(spotifyUri))
  }

}
