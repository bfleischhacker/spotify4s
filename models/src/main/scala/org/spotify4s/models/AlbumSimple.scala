package org.spotify4s.models

import org.spotify4s.util.{Enumerated, Identifiable}


/**
  * @param albumType        The type of the album: one of "album", "single", or "compilation".
  * @param availableMarkets The markets in which the album is available: [ISO 3166-1 alpha-2 country codes](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2). Note that an album is considered available in a market when at least 1 of its tracks is available in that market.
  * @param externalUrls
  * @param href             A link to the Web API endpoint providing full details of the album.
  * @param id
  * @param images
  * @param name             The name of the album.
  * @param `type`
  * @param uri
  **/
case class AlbumSimple(albumType: AlbumSimple.AlbumType,
                       availableMarkets: List[String],
                       externalUrls: ExternalUrl,
                       href: String,
                       id: SpotifyId,
                       images: List[Image],
                       name: String,
                       `type`: String,
                       uri: SpotifyUri)

object AlbumSimple {

  sealed abstract class AlbumType(val identifier: String) extends Identifiable

  object AlbumType extends Enumerated[AlbumType] {
    override val all: Seq[AlbumType] = Seq(Album, Single, Compilation)

    object Album extends AlbumType("album")

    object Single extends AlbumType("single")

    object Compilation extends AlbumType("compilation")

  }

}
