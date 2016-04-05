package org.spotify4s.resources

import org.spotify4s.SpotifyError
import org.spotify4s.models.{SearchResult, SpotifySearchType}

import scala.concurrent.Future

trait RealSearchResource {
  self: ApiResource =>

  val search: Search = new Search {}

  trait Search {
    def search(query: String,
               types: Set[SpotifySearchType],
               market: Option[String] = None,
               limit: Option[Int] = None,
               offset: Option[Int] = None): Future[Either[SpotifyError, SearchResult]] = {
      val params = List(
        Some("q" -> query),
        Some("type" -> (if (types.isEmpty) SpotifySearchType.all.toSet else types).mkString(",")),
        market.map("market" -> _),
        limit.map("limit" -> _.toString),
        offset.map("offset" -> _.toString)).flatten

      api.get[SearchResult]("/v1/search", params = params)
    }
  }

}



