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
  **/
case class TrackSimple(artists: List[ArtistSimple],
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
                       uri: SpotifyUri)

