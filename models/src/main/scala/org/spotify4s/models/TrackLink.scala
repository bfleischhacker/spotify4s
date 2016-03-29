package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}


/**
  * @param externalUrls Known external URLs for this track.
  * @param href         A link to the Web API endpoint providing full details of the track.
  * @param id
  * @param `type`       The object type: "track".
  * @param uri
  **/
case class TrackLink(externalUrls: ExternalUrl, href: String, id: SpotifyId, `type`: String, uri: SpotifyUri)

object TrackLink {
  implicit val decoder: Decoder[TrackLink] = (Decoder.instance(_.get[ExternalUrl]("external_urls")) |@| Decoder
    .instance(_.get[String]("href")) |@| Decoder.instance(_.get[SpotifyId]("id")) |@| Decoder
    .instance(_.get[String]("type")) |@| Decoder.instance(_.get[SpotifyUri]("uri"))).map(TrackLink.apply)

  implicit val encoder: Encoder[TrackLink] = Encoder
    .instance(trackLink => Json
      .obj("external_urls" -> Encoder[ExternalUrl].apply(trackLink.externalUrls),
        "href" -> Encoder[String].apply(trackLink.href),
        "id" -> Encoder[SpotifyId].apply(trackLink.id),
        "type" -> Encoder[String].apply(trackLink.`type`),
        "uri" -> Encoder[SpotifyUri].apply(trackLink.uri)))


}
