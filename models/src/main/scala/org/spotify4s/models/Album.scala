package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}
import org.spotify4s.util.{Enumerated, Identifiable}


/**
  * @param albumType            The type of the album: one of "album", "single", or "compilation".
  * @param availableMarkets     The markets in which the album is available: [ISO 3166-1 alpha-2 country codes](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2). Note that an album is considered available in a market when at least 1 of its tracks is available in that market.
  * @param externalUrls
  * @param href                 A link to the Web API endpoint providing full details of the album.
  * @param id
  * @param images
  * @param name                 The name of the album.
  * @param `type`
  * @param uri
  * @param artists
  * @param copyrights
  * @param externalIds
  * @param genres               A list of the genres used to classify the album. If not yet classified, the array is empty.
  * @param popularity           The popularity of the album. The value will be between 0 and 100, with 100 being the most popular. The popularity is calculated from the popularity of the album's individual tracks.
  * @param releaseDate          The date the album was first released, for example "1981-12-15". Depending on the precision, it might be shown as "1981" or "1981-12".
  * @param releaseDatePrecision The precision with which `release_date` value is known.
  * @param tracks               The tracks of the album.
  **/
case class Album(albumType: AlbumSimple.AlbumType,
                 availableMarkets: List[String],
                 externalUrls: ExternalUrl,
                 href: String,
                 id: SpotifyId,
                 images: List[Image],
                 name: String,
                 `type`: String,
                 uri: SpotifyUri,
                 artists: List[ArtistSimple],
                 copyrights: List[Copyright],
                 externalIds: ExternalId,
                 genres: List[String],
                 popularity: Int,
                 releaseDate: String,
                 releaseDatePrecision: Album.ReleaseDatePrecision,
                 tracks: Page[TrackSimple])

object Album {
  implicit val decoder: Decoder[Album] = (Decoder.instance(_.get[AlbumSimple.AlbumType]("album_type")) |@| Decoder
    .instance(_.get[List[String]]("available_markets")) |@| Decoder
    .instance(_.get[ExternalUrl]("external_urls")) |@| Decoder.instance(_.get[String]("href")) |@| Decoder
    .instance(_.get[SpotifyId]("id")) |@| Decoder.instance(_.get[List[Image]]("images")) |@| Decoder
    .instance(_.get[String]("name")) |@| Decoder.instance(_.get[String]("type")) |@| Decoder
    .instance(_.get[SpotifyUri]("uri")) |@| Decoder.instance(_.get[List[ArtistSimple]]("artists")) |@| Decoder
    .instance(_.get[List[Copyright]]("copyrights")) |@| Decoder.instance(_.get[ExternalId]("external_ids")) |@| Decoder
    .instance(_.get[List[String]]("genres")) |@| Decoder.instance(_.get[Int]("popularity")) |@| Decoder
    .instance(_.get[String]("release_date")) |@| Decoder
    .instance(_.get[Album.ReleaseDatePrecision]("release_date_precision")) |@| Decoder
    .instance(_.get[Page[TrackSimple]]("tracks"))).map(Album.apply)

  implicit val encoder: Encoder[Album] = Encoder
    .instance(album => Json
      .obj("album_type" -> Encoder[AlbumSimple.AlbumType].apply(album.albumType),
        "available_markets" -> Encoder[List[String]].apply(album.availableMarkets),
        "external_urls" -> Encoder[ExternalUrl].apply(album.externalUrls),
        "href" -> Encoder[String].apply(album.href),
        "id" -> Encoder[SpotifyId].apply(album.id),
        "images" -> Encoder[List[Image]].apply(album.images),
        "name" -> Encoder[String].apply(album.name),
        "type" -> Encoder[String].apply(album.`type`),
        "uri" -> Encoder[SpotifyUri].apply(album.uri),
        "artists" -> Encoder[List[ArtistSimple]].apply(album.artists),
        "copyrights" -> Encoder[List[Copyright]].apply(album.copyrights),
        "external_ids" -> Encoder[ExternalId].apply(album.externalIds),
        "genres" -> Encoder[List[String]].apply(album.genres),
        "popularity" -> Encoder[Int].apply(album.popularity),
        "release_date" -> Encoder[String].apply(album.releaseDate),
        "release_date_precision" -> Encoder[Album.ReleaseDatePrecision].apply(album.releaseDatePrecision),
        "tracks" -> Encoder[Page[TrackSimple]].apply(album.tracks)))


  sealed abstract class ReleaseDatePrecision(val identifier: String) extends Identifiable

  object ReleaseDatePrecision extends Enumerated[ReleaseDatePrecision] {
    override val all: Seq[ReleaseDatePrecision] = Seq(Year, Month, Day)

    object Year extends ReleaseDatePrecision("year")

    object Month extends ReleaseDatePrecision("month")

    object Day extends ReleaseDatePrecision("day")

  }

}
