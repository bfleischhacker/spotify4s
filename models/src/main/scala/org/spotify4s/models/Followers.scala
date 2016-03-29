package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}


/**
  * Information about the followers of an item.
  *
  * @param href  A link to the Web API endpoint providing full details of the followers; null if not available. Please note that this will always be set to `null`, as the Web API does not support it at the moment.
  * @param total The total number of followers.
  **/
case class Followers(href: String, total: Int)

object Followers {
  implicit val decoder: Decoder[Followers] = (Decoder.instance(_.get[String]("href")) |@| Decoder
    .instance(_.get[Int]("total"))).map(Followers.apply)

  implicit val encoder: Encoder[Followers] = Encoder
    .instance(followers => Json
      .obj("href" -> Encoder[String].apply(followers.href), "total" -> Encoder[Int].apply(followers.total)))


}
