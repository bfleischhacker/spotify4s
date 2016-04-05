package org.spotify4s.resources

import org.spotify4s.SpotifyError
import org.spotify4s.models._

import scala.concurrent.Future

trait PlaylistsResource {

  val playlists: Playlists

  trait Playlists {
    /**
      *
      * @param userId     The user's Spotify user ID
      * @param playlistId The Spotify ID for the playlist
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

    /**
      * @param userId   The user's Spotify User ID
      * @param name     The name for the new playlist, for example "Your Coolest Playlist". This name does not need to be unique; a user may have several playlists with the same name.
      * @param isPublic Optional (default true). If true the playlist will be public, if false it will be private. To be able to create private playlists, the user must have granted the playlist-modify-private scope.
      * @return
      */
    def createPlaylist(userId: String,
                       name: String,
                       isPublic: Option[Boolean] = None): Future[Either[SpotifyError, Playlist]]


    def replacePlaylistTracks(userId: String,
                              playlistId: String,
                              trackUris: List[String]): Future[Either[SpotifyError, Unit]]
  }

}
