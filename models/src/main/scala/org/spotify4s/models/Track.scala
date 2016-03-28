package org.spotify4s.models


/**
  * @param artists
  * @param availableMarkets A list of the countries in which the track can be played, identified by their [ISO 3166-1 alpha-2](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) code.
  * @param discNumber       The disc number (usually `1` unless the album consists of more than one disc).
  * @param durationMs       The track length in milliseconds.
  * @param explicit         Whether or not the track has explicit lyrics (`true` = yes it does; `false` = no it does not OR unknown).
  * @param externalUrls
  * @param href             A link to the Web API endpoint providing full details of the track.
  * @param id
  * @param isPlayable       Part of the response when [Track Relinking](https://developer.spotify. com/web-api/track-relinking-guide/) is applied. If `true`, the track is playable in the given market. Otherwise `false`.
  * @param linkedFrom       Part of the response when [Track Relinking](https://developer.spotify. com/web-api/track-relinking-guide/) is applied and is only part of the response if the track linking, in fact, exists. The requested track has been replaced with a different track. The track in the `linked_from` object contains information about the originally requested track.
  * @param name             The name of the track.
  * @param previewUrl       A URL to a 30 second preview (MP3 format) of the track.
  * @param trackNumber      The number of the track. If an album has several discs, the track number is the number on the specified disc.
  * @param `type`           The object type: "track".
  * @param uri
  * @param album            The album on which the track appears. The album object includes a link in `href` to full information about the album.
  * @param externalIds
  * @param popularity       The popularity of the track. The value will be between 0 and 100, with 100 being the most popular. The popularity of a track is a value between 0 and 100, with 100 being the most popular. The popularity is calculated by algorithm and is based, in the most part, on the total number of plays the track has had and how recent those plays are. Generally speaking, songs that are being played a lot now will have a higher popularity than songs that were played a lot in the past. Duplicate tracks (e.g. the same track from a single and an album) are rated independently. Artist and album popularity is derived mathematically from track popularity. Note that the popularity value may lag actual popularity by a few days: the value is not updated in real time.
  **/
case class Track(artists: List[ArtistSimple],
                 availableMarkets: List[String],
                 discNumber: Int,
                 durationMs: Int,
                 explicit: Boolean,
                 externalUrls: ExternalUrl,
                 href: String,
                 id: SpotifyId,
                 isPlayable: Option[Boolean],
                 linkedFrom: Option[TrackLink],
                 name: String,
                 previewUrl: String,
                 trackNumber: Int,
                 `type`: String,
                 uri: SpotifyUri,
                 album: AlbumSimple,
                 externalIds: ExternalId,
                 popularity: Int)

