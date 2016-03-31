package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}


/**
  * @param height The image height in pixels. If unknown: `null` or not returned.
  * @param url    The source URL of the image.
  * @param width  The image width in pixels. If unknown: `null` or not returned.
  **/
case class Image(height: Option[Int], url: String, width: Option[Int])

object Image {
  implicit val decoder: Decoder[Image] = (Decoder.instance(_.get[Option[Int]]("height")) |@| Decoder
    .instance(_.get[String]("url")) |@| Decoder.instance(_.get[Option[Int]]("width"))).map(Image.apply)

  implicit val encoder: Encoder[Image] = Encoder
    .instance(image => Json
      .obj("height" -> Encoder[Option[Int]].apply(image.height),
        "url" -> Encoder[String].apply(image.url),
        "width" -> Encoder[Option[Int]].apply(image.width)))


}
