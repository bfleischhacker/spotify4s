package org.spotify4s.models

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
                       id: SpotifyUserId,
                       images: Option[List[Image]],
                       `type`: String,
                       uri: SpotifyUri,
                       birthday: String,
                       country: String,
                       email: String,
                       product: UserPrivate.Product)

object UserPrivate {

  sealed abstract class Product(val identifier: String) extends Identifiable

  object Product extends Enumerated[Product] {
    override val all: Seq[Product] = Seq(Premium, Free, Open)

    object Premium extends Product("premium")

    object Free extends Product("free")

    object Open extends Product("open")

  }

}
