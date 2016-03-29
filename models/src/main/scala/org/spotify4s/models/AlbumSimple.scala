package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}
import org.spotify4s.util.{Enumerated, Identifiable}


/**
  * @param albumType        The type of the album: one of "album", "single", or "compilation".
  * @param availableMarkets The markets in which the album is available: [ISO 3166-1 alpha-2 country codes](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2). Note that an album is considered available in a market when at least 1 of its tracks is available in that market.
  * @param externalUrls
  * @param href             A link to the Web API endpoint providing full details of the album.
  * @param id
  * @param images
  * @param name             The name of the album.
  * @param `type`
  * @param uri
  **/
case class AlbumSimple(albumType: AlbumSimple.AlbumType,
                       availableMarkets: List[String],
                       externalUrls: ExternalUrl,
                       href: String,
                       id: SpotifyId,
                       images: List[Image],
                       name: String,
                       `type`: String,
                       uri: SpotifyUri)

object AlbumSimple {
  implicit val decoder: Decoder[AlbumSimple] = (Decoder.instance(_.get[AlbumSimple.AlbumType]("album_type")) |@| Decoder
    .instance(_.get[List[String]]("available_markets")) |@| Decoder
    .instance(_.get[ExternalUrl]("external_urls")) |@| Decoder.instance(_.get[String]("href")) |@| Decoder
    .instance(_.get[SpotifyId]("id")) |@| Decoder.instance(_.get[List[Image]]("images")) |@| Decoder
    .instance(_.get[String]("name")) |@| Decoder.instance(_.get[String]("type")) |@| Decoder
    .instance(_.get[SpotifyUri]("uri"))).map(AlbumSimple.apply)

  implicit val encoder: Encoder[AlbumSimple] = Encoder
    .instance(albumSimple => Json
      .obj("album_type" -> Encoder[AlbumSimple.AlbumType].apply(albumSimple.albumType),
        "available_markets" -> Encoder[List[String]].apply(albumSimple.availableMarkets),
        "external_urls" -> Encoder[ExternalUrl].apply(albumSimple.externalUrls),
        "href" -> Encoder[String].apply(albumSimple.href),
        "id" -> Encoder[SpotifyId].apply(albumSimple.id),
        "images" -> Encoder[List[Image]].apply(albumSimple.images),
        "name" -> Encoder[String].apply(albumSimple.name),
        "type" -> Encoder[String].apply(albumSimple.`type`),
        "uri" -> Encoder[SpotifyUri].apply(albumSimple.uri)))


  sealed abstract class AlbumType(val identifier: String) extends Identifiable

  object AlbumType extends Enumerated[AlbumType] {
    override val all: Seq[AlbumType] = Seq(Album, Single, Compilation)

    object Album extends AlbumType("album")

    object Single extends AlbumType("single")

    object Compilation extends AlbumType("compilation")

  }

}
