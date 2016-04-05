package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}


/**
  * A reference to a paged href
  *
  * @param href  A link to the Web API endpoint returning the full result of the request.
  * @param total The total number of items available to return.
  **/
case class PageReference(href: String, total: Int)

object PageReference {
  implicit val decoder: Decoder[PageReference] = (Decoder.instance(_.get[String]("href")) |@| Decoder
    .instance(_.get[Int]("total"))).map(PageReference.apply)

  implicit val encoder: Encoder[PageReference] = Encoder
    .instance(pageReference => Json
      .obj("href" -> Encoder[String].apply(pageReference.href), "total" -> Encoder[Int].apply(pageReference.total)))


}
