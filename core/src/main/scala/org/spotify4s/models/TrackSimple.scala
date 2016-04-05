package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}


/**
  * @param artists
  * @param availableMarkets A list of the countries in which the track can be played, identified by their [ISO 3166-1 alpha-2](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) code.
  * @param discNumber       The disc number (usually `1` unless the album consists of more than one disc).
  * @param durationMs       The track length in milliseconds.
  * @param explicit         Whether or not the track has explicit lyrics (`true` = yes it does; `false` = no it does not OR unknown).
  * @param externalUrls
  * @param href             A link to the Web API endpoint providing full details of the track.
  * @param id
  * @param isPlayable       Part of the response when [Track Relinking](https://developer.spotify. com/web-api/track-relinking-guide/) is applied. If `true`, the track is playable in the given market. Otherwise `false`.
  * @param linkedFrom       Part of the response when [Track Relinking](https://developer.spotify. com/web-api/track-relinking-guide/) is applied and is only part of the response if the track linking, in fact, exists. The requested track has been replaced with a different track. The track in the `linked_from` object contains information about the originally requested track.
  * @param name             The name of the track.
  * @param previewUrl       A URL to a 30 second preview (MP3 format) of the track.
  * @param trackNumber      The number of the track. If an album has several discs, the track number is the number on the specified disc.
  * @param `type`           The object type: "track".
  * @param uri
  **/
case class TrackSimple(artists: List[ArtistSimple],
                       availableMarkets: List[String],
                       discNumber: Int,
                       durationMs: Int,
                       explicit: Boolean,
                       externalUrls: ExternalUrl,
                       href: String,
                       id: String,
                       isPlayable: Option[Boolean],
                       linkedFrom: Option[TrackLink],
                       name: String,
                       previewUrl: Option[String],
                       trackNumber: Int,
                       `type`: String,
                       uri: String)

object TrackSimple {
  implicit val decoder: Decoder[TrackSimple] = (Decoder.instance(_.get[List[ArtistSimple]]("artists")) |@| Decoder
    .instance(_.get[List[String]]("available_markets")) |@| Decoder.instance(_.get[Int]("disc_number")) |@| Decoder
    .instance(_.get[Int]("duration_ms")) |@| Decoder.instance(_.get[Boolean]("explicit")) |@| Decoder
    .instance(_.get[ExternalUrl]("external_urls")) |@| Decoder.instance(_.get[String]("href")) |@| Decoder
    .instance(_.get[String]("id")) |@| Decoder.instance(_.get[Option[Boolean]]("is_playable")) |@| Decoder
    .instance(_.get[Option[TrackLink]]("linked_from")) |@| Decoder.instance(_.get[String]("name")) |@| Decoder
    .instance(_.get[Option[String]]("preview_url")) |@| Decoder.instance(_.get[Int]("track_number")) |@| Decoder
    .instance(_.get[String]("type")) |@| Decoder.instance(_.get[String]("uri"))).map(TrackSimple.apply)

  implicit val encoder: Encoder[TrackSimple] = Encoder
    .instance(trackSimple => Json
      .obj("artists" -> Encoder[List[ArtistSimple]].apply(trackSimple.artists),
        "available_markets" -> Encoder[List[String]].apply(trackSimple.availableMarkets),
        "disc_number" -> Encoder[Int].apply(trackSimple.discNumber),
        "duration_ms" -> Encoder[Int].apply(trackSimple.durationMs),
        "explicit" -> Encoder[Boolean].apply(trackSimple.explicit),
        "external_urls" -> Encoder[ExternalUrl].apply(trackSimple.externalUrls),
        "href" -> Encoder[String].apply(trackSimple.href),
        "id" -> Encoder[String].apply(trackSimple.id),
        "is_playable" -> Encoder[Option[Boolean]].apply(trackSimple.isPlayable),
        "linked_from" -> Encoder[Option[TrackLink]].apply(trackSimple.linkedFrom),
        "name" -> Encoder[String].apply(trackSimple.name),
        "preview_url" -> Encoder[Option[String]].apply(trackSimple.previewUrl),
        "track_number" -> Encoder[Int].apply(trackSimple.trackNumber),
        "type" -> Encoder[String].apply(trackSimple.`type`),
        "uri" -> Encoder[String].apply(trackSimple.uri)))


}
