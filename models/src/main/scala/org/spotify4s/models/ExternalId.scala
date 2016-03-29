package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}


case class ExternalId(isrc: Option[String], ean: Option[String], upc: Option[String])

object ExternalId {
  implicit val decoder: Decoder[ExternalId] = (Decoder.instance(_.get[Option[String]]("isrc")) |@| Decoder
    .instance(_.get[Option[String]]("ean")) |@| Decoder.instance(_.get[Option[String]]("upc"))).map(ExternalId.apply)

  implicit val encoder: Encoder[ExternalId] = Encoder
    .instance(externalId => Json
      .obj("isrc" -> Encoder[Option[String]].apply(externalId.isrc),
        "ean" -> Encoder[Option[String]].apply(externalId.ean),
        "upc" -> Encoder[Option[String]].apply(externalId.upc)))


}
