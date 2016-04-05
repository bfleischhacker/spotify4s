package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.syntax._
import io.circe.{Decoder, Encoder, Json}

case class SearchResult(artists: Option[Page[Artist]],
                        albums: Option[Page[Album]],
                        tracks: Option[Page[Track]],
                        playlists: Option[Page[PlaylistSimple]])

object SearchResult {

  implicit val decoder: Decoder[SearchResult] = {
    (Decoder.instance(_.get[Option[Page[Artist]]]("artists")) |@|
      Decoder.instance(_.get[Option[Page[Album]]]("album")) |@|
      Decoder.instance(_.get[Option[Page[Track]]]("track")) |@|
      Decoder.instance(_.get[Option[Page[PlaylistSimple]]]("playlists"))).map(SearchResult.apply)
  }

  implicit val encoder: Encoder[SearchResult] = Encoder.instance[SearchResult](searchResult =>
    Json.obj(List(
      searchResult.artists.map("artists" -> _.asJson),
      searchResult.albums.map("albums" -> _.asJson),
      searchResult.tracks.map("tracks" -> _.asJson),
      searchResult.playlists.map("playlists" -> _.asJson)
    ).flatten: _*)
  )

}