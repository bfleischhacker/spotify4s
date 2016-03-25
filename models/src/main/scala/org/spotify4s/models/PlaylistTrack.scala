package org.spotify4s.models


/**
  * @param addedAt The date and time the track was saved. Timestamps are returned in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format as [Coordinated Universal Time](http://en.wikipedia.org/wiki/   Offset_to_Coordinated_Universal_Time) (UTC) with zero offset. YYYY-MM-DDTHH:MM:SSZ.
  * @param addedBy
  * @param isLocal Whether this track is a [local file](https://developer.spotify.com/ web-api/local-files-spotify-playlists/) or not.
  * @param track   Information about the track.
  **/
case class PlaylistTrack(addedAt: String, addedBy: UserPublic, isLocal: Boolean, track: Track)

