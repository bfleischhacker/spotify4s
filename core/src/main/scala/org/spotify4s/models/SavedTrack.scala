package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}


/**
  * @param addedAt The date and time the track was saved. Timestamps are returned in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format as [Coordinated Universal Time](http://en.wikipedia.org/wiki/Offset _to_Coordinated_Universal_Time) (UTC) with zero offset. YYYY-MM-DDTHH:MM:SSZ.
  * @param track
  **/
case class SavedTrack(addedAt: String, track: Track)

object SavedTrack {
  implicit val decoder: Decoder[SavedTrack] = (Decoder.instance(_.get[String]("added_at")) |@| Decoder
    .instance(_.get[Track]("track"))).map(SavedTrack.apply)

  implicit val encoder: Encoder[SavedTrack] = Encoder
    .instance(savedTrack => Json
      .obj("added_at" -> Encoder[String].apply(savedTrack.addedAt), "track" -> Encoder[Track].apply(savedTrack.track)))


}
