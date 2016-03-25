package org.spotify4s.models


/**
  * @param addedAt The date and time the track was saved. Timestamps are returned in [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) format as [Coordinated Universal Time](http://en.wikipedia.org/wiki/Offset _to_Coordinated_Universal_Time) (UTC) with zero offset. YYYY-MM-DDTHH:MM:SSZ.
  * @param track
  **/
case class SavedTrack(addedAt: String, track: Track)

