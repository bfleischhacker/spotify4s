package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}
import org.spotify4s.util.{Enumerated, Identifiable}


/**
  * @param displayName The name displayed on the user's profile. `null` if not available.
  * @param externalUrls
  * @param followers
  * @param href        A link to the Web API endpoint for this user.
  * @param id
  * @param images      The user's profile image.
  * @param `type`      The object type: "user".
  * @param uri
  * @param birthday    The user's date-of-birth.
  * @param country     The country of the user, as set in the user's account profile. An [ISO 3166-1 alpha-2 country code](http://en.wikipedia.org/wiki/ ISO_3166-1_alpha-2).
  * @param email       The user's email address, as entered by the user when creating their account. **Important!** This email address is unverified; there is no proof that it actually belongs to the user.
  * @param product     The user's Spotify subscription level: "premium", "free", etc. (The subscription level "open" can be considered the same as "free".)
  **/
case class UserPrivate(displayName: Option[String],
                       externalUrls: ExternalUrl,
                       followers: Option[Followers],
                       href: String,
                       id: String,
                       images: Option[List[Image]],
                       `type`: String,
                       uri: String,
                       birthday: String,
                       country: String,
                       email: String,
                       product: UserPrivate.Product)

object UserPrivate {
  implicit val decoder: Decoder[UserPrivate] = (Decoder.instance(_.get[Option[String]]("display_name")) |@| Decoder
    .instance(_.get[ExternalUrl]("external_urls")) |@| Decoder
    .instance(_.get[Option[Followers]]("followers")) |@| Decoder.instance(_.get[String]("href")) |@| Decoder
    .instance(_.get[String]("id")) |@| Decoder.instance(_.get[Option[List[Image]]]("images")) |@| Decoder
    .instance(_.get[String]("type")) |@| Decoder.instance(_.get[String]("uri")) |@| Decoder
    .instance(_.get[String]("birthday")) |@| Decoder.instance(_.get[String]("country")) |@| Decoder
    .instance(_.get[String]("email")) |@| Decoder.instance(_.get[UserPrivate.Product]("product")))
    .map(UserPrivate.apply)

  implicit val encoder: Encoder[UserPrivate] = Encoder
    .instance(userPrivate => Json
      .obj("display_name" -> Encoder[Option[String]].apply(userPrivate.displayName),
        "external_urls" -> Encoder[ExternalUrl].apply(userPrivate.externalUrls),
        "followers" -> Encoder[Option[Followers]].apply(userPrivate.followers),
        "href" -> Encoder[String].apply(userPrivate.href),
        "id" -> Encoder[String].apply(userPrivate.id),
        "images" -> Encoder[Option[List[Image]]].apply(userPrivate.images),
        "type" -> Encoder[String].apply(userPrivate.`type`),
        "uri" -> Encoder[String].apply(userPrivate.uri),
        "birthday" -> Encoder[String].apply(userPrivate.birthday),
        "country" -> Encoder[String].apply(userPrivate.country),
        "email" -> Encoder[String].apply(userPrivate.email),
        "product" -> Encoder[UserPrivate.Product].apply(userPrivate.product)))


  sealed abstract class Product(val identifier: String) extends Identifiable

  object Product extends Enumerated[Product] {
    override val all: Seq[Product] = Seq(Premium, Free, Open)

    object Premium extends Product("premium")

    object Free extends Product("free")

    object Open extends Product("open")

  }

}
