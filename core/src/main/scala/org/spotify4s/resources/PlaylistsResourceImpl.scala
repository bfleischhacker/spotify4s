package org.spotify4s.resources

import org.spotify4s.SpotifyError
import org.spotify4s.models._

import scala.concurrent.Future

trait PlaylistsResourceImpl extends PlaylistsResource {
  self: AbstractApiResource =>

  override val playlists: Playlists = new Playlists {
    override def getPlaylist(userId: SpotifyUserId,
                             playlistId: SpotifyId,
                             markets: Option[String]): Future[Either[SpotifyError, PlaylistSimple]] = {
      val params = markets.map("market" -> _).toMap
      api.get[PlaylistSimple](s"/v1/users/${userId.spotifyUserId}/playlists/${playlistId.spotifyId}", params = params)
    }

    override def getPlaylists(userId: SpotifyUserId,
                              limit: Option[Int],
                              offset: Option[Int]): Future[Either[SpotifyError, Page[PlaylistSimple]]] = {
      val params = List(limit.map("limit" -> _.toString), offset.map("offset" -> _.toString)).flatten.toMap
      api.get[Page[PlaylistSimple]](s"/v1/users/${userId.spotifyUserId}/playlists", params = params)
    }

    override def getPlaylistTracks(userId: SpotifyUserId,
                                   playlistId: SpotifyId,
                                   market: Option[String],
                                   limit: Option[Int],
                                   offset: Option[Int]): Future[Either[SpotifyError, Page[PlaylistTrack]]] = {
      val params = List(
        limit.map("limit" -> _.toString),
        offset.map("offset" -> _.toString),
        market.map("market" -> _)
      ).flatten.toMap

      api.get[Page[PlaylistTrack]](s"/v1/users/${userId.spotifyUserId}/playlists/${playlistId.spotifyId}/tracks",
        params)
    }
  }

}
