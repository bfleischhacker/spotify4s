package org.spotify4s.models


/**
  * @param externalUrls Known external URLs for this track.
  * @param href         A link to the Web API endpoint providing full details of the track.
  * @param id
  * @param `type`       The object type: "track".
  * @param uri
  **/
case class TrackLink(externalUrls: ExternalUrl, href: String, id: SpotifyId, `type`: String, uri: SpotifyUri)

