package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}


/**
  * @param addedAt The date and time the track was saved. Timestamps are returned in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format as [Coordinated Universal Time](http://en.wikipedia.org/wiki/   Offset_to_Coordinated_Universal_Time) (UTC) with zero offset. YYYY-MM-DDTHH:MM:SSZ.
  * @param addedBy
  * @param isLocal Whether this track is a [local file](https://developer.spotify.com/ web-api/local-files-spotify-playlists/) or not.
  * @param track   Information about the track.
  **/
case class PlaylistTrack(addedAt: String, addedBy: UserPublic, isLocal: Boolean, track: Track)

object PlaylistTrack {
  implicit val decoder: Decoder[PlaylistTrack] = (Decoder.instance(_.get[String]("added_at")) |@| Decoder
    .instance(_.get[UserPublic]("added_by")) |@| Decoder.instance(_.get[Boolean]("is_local")) |@| Decoder
    .instance(_.get[Track]("track"))).map(PlaylistTrack.apply)

  implicit val encoder: Encoder[PlaylistTrack] = Encoder
    .instance(playlistTrack => Json
      .obj("added_at" -> Encoder[String].apply(playlistTrack.addedAt),
        "added_by" -> Encoder[UserPublic].apply(playlistTrack.addedBy),
        "is_local" -> Encoder[Boolean].apply(playlistTrack.isLocal),
        "track" -> Encoder[Track].apply(playlistTrack.track)))


}
