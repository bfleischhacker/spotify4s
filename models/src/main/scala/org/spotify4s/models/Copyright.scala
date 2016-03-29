package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}
import org.spotify4s.util.{Enumerated, Identifiable}


/**
  * @param text   The copyright text for this album.
  * @param `type` The type of copyright: C = the copyright, P = the sound recording (performance) copyright.
  **/
case class Copyright(text: String, `type`: Copyright.Type)

object Copyright {
  implicit val decoder: Decoder[Copyright] = (Decoder.instance(_.get[String]("text")) |@| Decoder
    .instance(_.get[Copyright.Type]("type"))).map(Copyright.apply)

  implicit val encoder: Encoder[Copyright] = Encoder
    .instance(copyright => Json
      .obj("text" -> Encoder[String].apply(copyright.text), "type" -> Encoder[Copyright.Type].apply(copyright.`type`)))


  sealed abstract class Type(val identifier: String) extends Identifiable

  object Type extends Enumerated[Type] {
    override val all: Seq[Type] = Seq(C, P)

    object C extends Type("C")

    object P extends Type("P")

  }

}
