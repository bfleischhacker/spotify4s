package org.spotify4s.resources

import org.spotify4s.SpotifyError
import org.spotify4s.models._

import scala.concurrent.Future

trait PlaylistsResource {

  val playlists: Playlists

  trait Playlists {
    /**
      *
      * @param userId     The user's [[SpotifyUserId Spotify user ID]]
      * @param playlistId The [[SpotifyId Spotify ID]] for the playlist
      * @param market     optional 2 character ISO 3166-1 country code, to allow [[https://developer.spotify.com/web-api/track-relinking-guide/ track relinking]]
      * @return
      */
    def getPlaylist(userId: String,
                    playlistId: String,
                    market: Option[String] = None): Future[Either[SpotifyError, PlaylistSimple]]

    def getPlaylists(userId: String,
                     limit: Option[Int] = None,
                     offset: Option[Int] = None): Future[Either[SpotifyError, Page[PlaylistSimple]]]

    def getPlaylistTracks(userId: String,
                          playlistId: String,
                          market: Option[String] = None,
                          limit: Option[Int] = None,
                          offset: Option[Int] = None): Future[Either[SpotifyError, Page[PlaylistTrack]]]
  }

}
