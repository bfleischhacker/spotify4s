package org.spotify4s.resources

import io.circe.Json
import io.circe.syntax._
import org.spotify4s.SpotifyError
import org.spotify4s.models._

import scala.concurrent.Future

trait RealPlaylistsResource extends PlaylistsResource {
  self: ApiResource =>

  override val playlists: Playlists = new Playlists {
    override def getPlaylist(userId: String,
                             playlistId: String,
                             markets: Option[String]): Future[Either[SpotifyError, PlaylistSimple]] = {
      val params = markets.map("market" -> _).toSeq
      api.get[PlaylistSimple](s"/v1/users/$userId/playlists/$playlistId", params = params)
    }

    override def getPlaylists(userId: String,
                              limit: Option[Int],
                              offset: Option[Int]): Future[Either[SpotifyError, Page[PlaylistSimple]]] = {
      val params = List(limit.map("limit" -> _.toString), offset.map("offset" -> _.toString)).flatten
      api.get[Page[PlaylistSimple]](s"/v1/users/$userId/playlists", params = params)
    }

    override def getPlaylistTracks(userId: String,
                                   playlistId: String,
                                   market: Option[String],
                                   limit: Option[Int],
                                   offset: Option[Int]): Future[Either[SpotifyError, Page[PlaylistTrack]]] = {
      val params = List(
        limit.map("limit" -> _.toString),
        offset.map("offset" -> _.toString),
        market.map("market" -> _)
      ).flatten

      api.get[Page[PlaylistTrack]](s"/v1/users/$userId/playlists/$playlistId/tracks", params = params)
    }

    override def createPlaylist(userId: String,
                                name: String,
                                isPublic: Option[Boolean]): Future[Either[SpotifyError, Playlist]] = {
      val body = Json.obj(List(Some("name" -> name.asJson), isPublic.map("public" -> _.asJson)).flatten: _*)
      api.post[Playlist](s"/v1/users/$userId/playlists",
        headers = Map("Content-Type" -> "application/json"),
        body = body)
    }

    override def replacePlaylistTracks(userId: String,
                                       playlistId: String,
                                       trackIds: List[String]): Future[Either[SpotifyError, Unit]] = {
      val body = Json.obj("uris" -> Json.array(trackIds.map(trackId => Json.string(s"spotify:track:$trackId")): _*))
      api.put[Unit](s"/v1/users/$userId/playlists/$playlistId/tracks",
        headers = Map("Content-Type" -> "application/json"),
        body = body)
    }
  }

}
