package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}


/**
  * @param collaborative `true` if the owner allows other users to modify the playlist.
  * @param externalUrls
  * @param href          A link to the Web API endpoint providing full details of the playlist.
  * @param id
  * @param images        Images for the playlist. The array may be empty or contain up to three images. The images are returned by size in descending order. See [Working with Playlists](https://developer.spotify.com/web-api/working-with-playlists/). Note: If returned, the source URL for the image (url) is temporary and will expire in less than a day.
  * @param name          The name of the playlist.
  * @param owner         The user who owns the playlist.
  * @param public        The playlist's public/private status: `true` the playlist is public, `false` the playlist is private, `null` the playlist status is not relevant. For more about public/private status, see [Working with Playlists](https://developer.spotify.com/web-api/working-with-playlists/).
  * @param snapshotId    The version identifier for the current playlist. Can be supplied in other requests to target a specific playlist version: see [Remove tracks from a playlist](https://developer.spotify.com/web-api/remove-tracks-playlist/).
  * @param tracks        Information about the tracks of the playlist.
  * @param `type`        The object type: "playlist"
  * @param uri
  **/
case class PlaylistSimple(collaborative: Boolean,
                          externalUrls: ExternalUrl,
                          href: String,
                          id: SpotifyId,
                          images: List[Image],
                          name: String,
                          owner: UserPublic,
                          public: Option[Boolean],
                          snapshotId: String,
                          tracks: PageReference,
                          `type`: String,
                          uri: SpotifyUri)

object PlaylistSimple {
  implicit val decoder: Decoder[PlaylistSimple] = (Decoder.instance(_.get[Boolean]("collaborative")) |@| Decoder
    .instance(_.get[ExternalUrl]("external_urls")) |@| Decoder.instance(_.get[String]("href")) |@| Decoder
    .instance(_.get[SpotifyId]("id")) |@| Decoder.instance(_.get[List[Image]]("images")) |@| Decoder
    .instance(_.get[String]("name")) |@| Decoder.instance(_.get[UserPublic]("owner")) |@| Decoder
    .instance(_.get[Option[Boolean]]("public")) |@| Decoder.instance(_.get[String]("snapshot_id")) |@| Decoder
    .instance(_.get[PageReference]("tracks")) |@| Decoder.instance(_.get[String]("type")) |@| Decoder
    .instance(_.get[SpotifyUri]("uri"))).map(PlaylistSimple.apply)

  implicit val encoder: Encoder[PlaylistSimple] = Encoder
    .instance(playlistSimple => Json
      .obj("collaborative" -> Encoder[Boolean].apply(playlistSimple.collaborative),
        "external_urls" -> Encoder[ExternalUrl].apply(playlistSimple.externalUrls),
        "href" -> Encoder[String].apply(playlistSimple.href),
        "id" -> Encoder[SpotifyId].apply(playlistSimple.id),
        "images" -> Encoder[List[Image]].apply(playlistSimple.images),
        "name" -> Encoder[String].apply(playlistSimple.name),
        "owner" -> Encoder[UserPublic].apply(playlistSimple.owner),
        "public" -> Encoder[Option[Boolean]].apply(playlistSimple.public),
        "snapshot_id" -> Encoder[String].apply(playlistSimple.snapshotId),
        "tracks" -> Encoder[PageReference].apply(playlistSimple.tracks),
        "type" -> Encoder[String].apply(playlistSimple.`type`),
        "uri" -> Encoder[SpotifyUri].apply(playlistSimple.uri)))


}
