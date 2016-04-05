package org.spotify4s.models

import org.spotify4s.util.{Enumerated, Identifiable}

sealed abstract class SpotifySearchType(val identifier: String) extends Identifiable

object SpotifySearchType extends Enumerated[SpotifySearchType] {
  override val all: Seq[SpotifySearchType] = Seq(Album)

  object Album extends SpotifySearchType("album")

  object Artist extends SpotifySearchType("artist")

  object Playlist extends SpotifySearchType("playlist")

  object Track extends SpotifySearchType("track")

}
