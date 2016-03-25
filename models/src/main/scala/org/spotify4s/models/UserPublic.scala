package org.spotify4s.models


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
case class UserPublic(displayName: Option[String], externalUrls: ExternalUrl, followers: Followers, href: String, id: SpotifyUserId, images: List[Image], `type`: String, uri: SpotifyUri)

