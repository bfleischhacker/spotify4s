package org.spotify4s.resources

import io.circe.{Decoder, Json}
import org.spotify4s.SpotifyError

import scala.concurrent.Future

trait AbstractApiResource {
  protected val api: Api
  trait Api {
    def get[T: Decoder](path: String,
                                  params: Map[String, String] = Map(),
                                  headers: Map[String, String] = Map()): Future[Either[SpotifyError, T]]

    def post[T: Decoder](path: String,
                                   params: Map[String, String] = Map(),
                                   headers: Map[String, String] = Map(),
                                   body: Json = Json.Empty): Future[Either[SpotifyError, T]]

    def delete[T: Decoder](path: String,
                                     params: Map[String, String] = Map(),
                                     headers: Map[String, String] = Map()): Future[Either[SpotifyError, T]]

    def put[T: Decoder](path: String,
                                  params: Map[String, String] = Map(),
                                  headers: Map[String, String] = Map(),
                                  body: Json = Json.Empty): Future[Either[SpotifyError, T]]
  }
}

