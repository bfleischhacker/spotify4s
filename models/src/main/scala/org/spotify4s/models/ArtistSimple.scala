package org.spotify4s.models


/**
  * @param externalUrls
  * @param href   A link to the Web API endpoint providing full details of the artist.
  * @param id
  * @param name   The name of the artist.
  * @param `type` The object type: "artist"
  * @param uri
  **/
case class ArtistSimple(externalUrls: ExternalUrl,
                        href: String,
                        id: SpotifyId,
                        name: String,
                        `type`: String,
                        uri: SpotifyUri)

