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
  * @param album            The album on which the track appears. The album object includes a link in `href` to full information about the album.
  * @param externalIds
  * @param popularity       The popularity of the track. The value will be between 0 and 100, with 100 being the most popular. The popularity of a track is a value between 0 and 100, with 100 being the most popular. The popularity is calculated by algorithm and is based, in the most part, on the total number of plays the track has had and how recent those plays are. Generally speaking, songs that are being played a lot now will have a higher popularity than songs that were played a lot in the past. Duplicate tracks (e.g. the same track from a single and an album) are rated independently. Artist and album popularity is derived mathematically from track popularity. Note that the popularity value may lag actual popularity by a few days: the value is not updated in real time.
  **/
case class Track(artists: List[ArtistSimple],
                 availableMarkets: List[String],
                 discNumber: Int,
                 durationMs: Int,
                 explicit: Boolean,
                 externalUrls: ExternalUrl,
                 href: String,
                 id: SpotifyId,
                 isPlayable: Option[Boolean],
                 linkedFrom: Option[TrackLink],
                 name: String,
                 previewUrl: String,
                 trackNumber: Int,
                 `type`: String,
                 uri: SpotifyUri,
                 album: AlbumSimple,
                 externalIds: ExternalId,
                 popularity: Int)

object Track {
  implicit val decoder: Decoder[Track] = (Decoder.instance(_.get[List[ArtistSimple]]("artists")) |@| Decoder
    .instance(_.get[List[String]]("available_markets")) |@| Decoder.instance(_.get[Int]("disc_number")) |@| Decoder
    .instance(_.get[Int]("duration_ms")) |@| Decoder.instance(_.get[Boolean]("explicit")) |@| Decoder
    .instance(_.get[ExternalUrl]("external_urls")) |@| Decoder.instance(_.get[String]("href")) |@| Decoder
    .instance(_.get[SpotifyId]("id")) |@| Decoder.instance(_.get[Option[Boolean]]("is_playable")) |@| Decoder
    .instance(_.get[Option[TrackLink]]("linked_from")) |@| Decoder.instance(_.get[String]("name")) |@| Decoder
    .instance(_.get[String]("preview_url")) |@| Decoder.instance(_.get[Int]("track_number")) |@| Decoder
    .instance(_.get[String]("type")) |@| Decoder.instance(_.get[SpotifyUri]("uri")) |@| Decoder
    .instance(_.get[AlbumSimple]("album")) |@| Decoder.instance(_.get[ExternalId]("external_ids")) |@| Decoder
    .instance(_.get[Int]("popularity"))).map(Track.apply)

  implicit val encoder: Encoder[Track] = Encoder
    .instance(track => Json
      .obj("artists" -> Encoder[List[ArtistSimple]].apply(track.artists),
        "available_markets" -> Encoder[List[String]].apply(track.availableMarkets),
        "disc_number" -> Encoder[Int].apply(track.discNumber),
        "duration_ms" -> Encoder[Int].apply(track.durationMs),
        "explicit" -> Encoder[Boolean].apply(track.explicit),
        "external_urls" -> Encoder[ExternalUrl].apply(track.externalUrls),
        "href" -> Encoder[String].apply(track.href),
        "id" -> Encoder[SpotifyId].apply(track.id),
        "is_playable" -> Encoder[Option[Boolean]].apply(track.isPlayable),
        "linked_from" -> Encoder[Option[TrackLink]].apply(track.linkedFrom),
        "name" -> Encoder[String].apply(track.name),
        "preview_url" -> Encoder[String].apply(track.previewUrl),
        "track_number" -> Encoder[Int].apply(track.trackNumber),
        "type" -> Encoder[String].apply(track.`type`),
        "uri" -> Encoder[SpotifyUri].apply(track.uri),
        "album" -> Encoder[AlbumSimple].apply(track.album),
        "external_ids" -> Encoder[ExternalId].apply(track.externalIds),
        "popularity" -> Encoder[Int].apply(track.popularity)))


}
