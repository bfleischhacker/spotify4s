package org.spotify4s.models

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
  private val pattern: Regex = "^[a-zA-Z0-9]+$".r


  def verified(spotifyUserId: String): Option[SpotifyUserId] = {
    pattern.findFirstMatchIn(spotifyUserId).map(_ => SpotifyUserId(spotifyUserId))
  }

}
