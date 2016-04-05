package org.spotify4s.resources

import org.spotify4s.SpotifyError
import org.spotify4s.models.{SearchResult, SpotifySearchType}

import scala.concurrent.Future

trait SearchResource {
  val search: Search

  trait Search {
    def search(query: String,
               types: Set[SpotifySearchType],
               market: Option[String] = None,
               limit: Option[Int] = None,
               offset: Option[Int] = None): Future[Either[SpotifyError, SearchResult]]
  }


}
