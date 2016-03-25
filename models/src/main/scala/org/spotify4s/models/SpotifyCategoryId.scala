package org.spotify4s.models

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


  def verified(spotifyCategoryId: String): Option[SpotifyCategoryId] = {
    pattern.findFirstMatchIn(spotifyCategoryId).map(_ => SpotifyCategoryId(spotifyCategoryId))
  }

}
