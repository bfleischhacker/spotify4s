package org.spotify4s.models

import org.spotify4s.util.{Enumerated, Identifiable}


/**
  * @param albumType            The type of the album: one of "album", "single", or "compilation".
  * @param availableMarkets     The markets in which the album is available: [ISO 3166-1 alpha-2 country codes](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2). Note that an album is considered available in a market when at least 1 of its tracks is available in that market.
  * @param externalUrls
  * @param href                 A link to the Web API endpoint providing full details of the album.
  * @param id
  * @param images
  * @param name                 The name of the album.
  * @param `type`
  * @param uri
  * @param artists
  * @param copyrights
  * @param externalIds
  * @param genres               A list of the genres used to classify the album. If not yet classified, the array is empty.
  * @param popularity           The popularity of the album. The value will be between 0 and 100, with 100 being the most popular. The popularity is calculated from the popularity of the album's individual tracks.
  * @param releaseDate          The date the album was first released, for example "1981-12-15". Depending on the precision, it might be shown as "1981" or "1981-12".
  * @param releaseDatePrecision The precision with which `release_date` value is known.
  * @param tracks               The tracks of the album.
  **/
case class Album(albumType: AlbumSimple.AlbumType,
                 availableMarkets: List[String],
                 externalUrls: ExternalUrl,
                 href: String,
                 id: SpotifyId,
                 images: List[Image],
                 name: String,
                 `type`: String,
                 uri: SpotifyUri,
                 artists: List[ArtistSimple],
                 copyrights: List[Copyright],
                 externalIds: ExternalId,
                 genres: List[String],
                 popularity: Int,
                 releaseDate: String,
                 releaseDatePrecision: Album.ReleaseDatePrecision,
                 tracks: Page[TrackSimple])

object Album {

  sealed abstract class ReleaseDatePrecision(val identifier: String) extends Identifiable

  object ReleaseDatePrecision extends Enumerated[ReleaseDatePrecision] {
    override val all: Seq[ReleaseDatePrecision] = Seq(Year, Month, Day)

    object Year extends ReleaseDatePrecision("year")

    object Month extends ReleaseDatePrecision("month")

    object Day extends ReleaseDatePrecision("day")

  }

}
