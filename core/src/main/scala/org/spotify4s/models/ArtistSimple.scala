package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}


/**
  * @param externalUrls
  * @param href   A link to the Web API endpoint providing full details of the artist.
  * @param id
  * @param name   The name of the artist.
  * @param `type` The object type: "artist"
  * @param uri
  **/
case class ArtistSimple(externalUrls: ExternalUrl, href: String, id: String, name: String, `type`: String, uri: String)

object ArtistSimple {
  implicit val decoder: Decoder[ArtistSimple] = (Decoder.instance(_.get[ExternalUrl]("external_urls")) |@| Decoder
    .instance(_.get[String]("href")) |@| Decoder.instance(_.get[String]("id")) |@| Decoder
    .instance(_.get[String]("name")) |@| Decoder.instance(_.get[String]("type")) |@| Decoder
    .instance(_.get[String]("uri"))).map(ArtistSimple.apply)

  implicit val encoder: Encoder[ArtistSimple] = Encoder
    .instance(artistSimple => Json
      .obj("external_urls" -> Encoder[ExternalUrl].apply(artistSimple.externalUrls),
        "href" -> Encoder[String].apply(artistSimple.href),
        "id" -> Encoder[String].apply(artistSimple.id),
        "name" -> Encoder[String].apply(artistSimple.name),
        "type" -> Encoder[String].apply(artistSimple.`type`),
        "uri" -> Encoder[String].apply(artistSimple.uri)))


}
