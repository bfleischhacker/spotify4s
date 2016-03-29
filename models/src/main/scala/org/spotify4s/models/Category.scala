package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}


/**
  * @param href A link to the Web API endpoint returning full details of the category.
  * @param icons
  * @param id
  * @param name The name of the category.
  **/
case class Category(href: String, icons: List[Image], id: SpotifyCategoryId, name: String)

object Category {
  implicit val decoder: Decoder[Category] = (Decoder.instance(_.get[String]("href")) |@| Decoder
    .instance(_.get[List[Image]]("icons")) |@| Decoder.instance(_.get[SpotifyCategoryId]("id")) |@| Decoder
    .instance(_.get[String]("name"))).map(Category.apply)

  implicit val encoder: Encoder[Category] = Encoder
    .instance(category => Json
      .obj("href" -> Encoder[String].apply(category.href),
        "icons" -> Encoder[List[Image]].apply(category.icons),
        "id" -> Encoder[SpotifyCategoryId].apply(category.id),
        "name" -> Encoder[String].apply(category.name)))


}
