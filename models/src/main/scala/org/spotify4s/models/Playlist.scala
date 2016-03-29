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
  * @param description   The playlist description. Only returned for modified, verified playlists, otherwise `null`.
  * @param followers     Information about the followers of the playlist.
  **/
case class Playlist(collaborative: Boolean,
                    externalUrls: ExternalUrl,
                    href: String,
                    id: SpotifyId,
                    images: List[Image],
                    name: String,
                    owner: UserPublic,
                    public: Boolean,
                    snapshotId: String,
                    tracks: PageReference,
                    `type`: String,
                    uri: SpotifyUri,
                    description: String,
                    followers: Followers)

object Playlist {
  implicit val decoder: Decoder[Playlist] = (Decoder.instance(_.get[Boolean]("collaborative")) |@| Decoder
    .instance(_.get[ExternalUrl]("external_urls")) |@| Decoder.instance(_.get[String]("href")) |@| Decoder
    .instance(_.get[SpotifyId]("id")) |@| Decoder.instance(_.get[List[Image]]("images")) |@| Decoder
    .instance(_.get[String]("name")) |@| Decoder.instance(_.get[UserPublic]("owner")) |@| Decoder
    .instance(_.get[Boolean]("public")) |@| Decoder.instance(_.get[String]("snapshot_id")) |@| Decoder
    .instance(_.get[PageReference]("tracks")) |@| Decoder.instance(_.get[String]("type")) |@| Decoder
    .instance(_.get[SpotifyUri]("uri")) |@| Decoder.instance(_.get[String]("description")) |@| Decoder
    .instance(_.get[Followers]("followers"))).map(Playlist.apply)

  implicit val encoder: Encoder[Playlist] = Encoder
    .instance(playlist => Json
      .obj("collaborative" -> Encoder[Boolean].apply(playlist.collaborative),
        "external_urls" -> Encoder[ExternalUrl].apply(playlist.externalUrls),
        "href" -> Encoder[String].apply(playlist.href),
        "id" -> Encoder[SpotifyId].apply(playlist.id),
        "images" -> Encoder[List[Image]].apply(playlist.images),
        "name" -> Encoder[String].apply(playlist.name),
        "owner" -> Encoder[UserPublic].apply(playlist.owner),
        "public" -> Encoder[Boolean].apply(playlist.public),
        "snapshot_id" -> Encoder[String].apply(playlist.snapshotId),
        "tracks" -> Encoder[PageReference].apply(playlist.tracks),
        "type" -> Encoder[String].apply(playlist.`type`),
        "uri" -> Encoder[SpotifyUri].apply(playlist.uri),
        "description" -> Encoder[String].apply(playlist.description),
        "followers" -> Encoder[Followers].apply(playlist.followers)))


}
