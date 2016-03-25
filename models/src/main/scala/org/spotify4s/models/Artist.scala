package org.spotify4s.models


/**
  * @param externalUrls
  * @param href       A link to the Web API endpoint providing full details of the artist.
  * @param id
  * @param name       The name of the artist.
  * @param `type`     The object type: "artist"
  * @param uri
  * @param followers
  * @param genres     A list of the genres the artist is associated with. If not yet classified, the array is empty.
  * @param images
  * @param popularity The popularity of the artist. The value will be between 0 and 100, with 100 being the most popular. The artist's popularity is calculated from the popularity of all the artist's tracks.
  **/
case class Artist(externalUrls: ExternalUrl, href: String, id: SpotifyId, name: String, `type`: String, uri: SpotifyUri, followers: Followers, genres: List[String], images: List[Image], popularity: Int)

