package org.spotify4s.models

import io.circe.{Decoder, Encoder, Json}


case class ExternalUrl(spotify: Option[String])

object ExternalUrl {
  implicit val decoder: Decoder[ExternalUrl] = (Decoder.instance(_.get[Option[String]]("spotify")))
    .map(ExternalUrl.apply)

  implicit val encoder: Encoder[ExternalUrl] = Encoder
    .instance(externalUrl => Json.obj("spotify" -> Encoder[Option[String]].apply(externalUrl.spotify)))


}
