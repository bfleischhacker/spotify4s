package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}


/**
  * @param displayName The name displayed on the user's profile. `null` if not available.
  * @param externalUrls
  * @param followers
  * @param href        A link to the Web API endpoint for this user.
  * @param id
  * @param images      The user's profile image.
  * @param `type`      The object type: "user".
  * @param uri
  **/
case class UserPublic(displayName: Option[String],
                      externalUrls: ExternalUrl,
                      followers: Option[Followers],
                      href: String,
                      id: SpotifyUserId,
                      images: Option[List[Image]],
                      `type`: String,
                      uri: SpotifyUri)

object UserPublic {
  implicit val decoder: Decoder[UserPublic] = (Decoder.instance(_.get[Option[String]]("display_name")) |@| Decoder
    .instance(_.get[ExternalUrl]("external_urls")) |@| Decoder
    .instance(_.get[Option[Followers]]("followers")) |@| Decoder.instance(_.get[String]("href")) |@| Decoder
    .instance(_.get[SpotifyUserId]("id")) |@| Decoder.instance(_.get[Option[List[Image]]]("images")) |@| Decoder
    .instance(_.get[String]("type")) |@| Decoder.instance(_.get[SpotifyUri]("uri"))).map(UserPublic.apply)

  implicit val encoder: Encoder[UserPublic] = Encoder
    .instance(userPublic => Json
      .obj("display_name" -> Encoder[Option[String]].apply(userPublic.displayName),
        "external_urls" -> Encoder[ExternalUrl].apply(userPublic.externalUrls),
        "followers" -> Encoder[Option[Followers]].apply(userPublic.followers),
        "href" -> Encoder[String].apply(userPublic.href),
        "id" -> Encoder[SpotifyUserId].apply(userPublic.id),
        "images" -> Encoder[Option[List[Image]]].apply(userPublic.images),
        "type" -> Encoder[String].apply(userPublic.`type`),
        "uri" -> Encoder[SpotifyUri].apply(userPublic.uri)))


}
