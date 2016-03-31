package org.spotify4s

import io.circe.{Decoder, Json}
import org.spotify4s.resources.{AbstractApiResource, PlaylistsResourceImpl, SearchResourceImpl}

import scala.concurrent.{ExecutionContext, Future}
import scalaj.http.{Http, HttpRequest}

case class SpotifyApi(accessToken: String)
                     (implicit executionContext: ExecutionContext) extends AbstractApiResource
  with PlaylistsResourceImpl with SearchResourceImpl {

  override protected val api: Api = new Api {
    private val ApiHost = "https://api.spotify.com"

    def execute[T: Decoder](request: HttpRequest): Future[Either[SpotifyError, T]] = {
      Future(HttpUtil.execute(request.header("Authorization", s"Bearer $accessToken")))
    }

    override def get[T: Decoder](path: String,
                                 params: Map[String, String],
                                 headers: Map[String, String]): Future[Either[SpotifyError, T]] = {
      execute(Http(s"$ApiHost$path")
        .method("GET")
        .params(params)
        .headers(headers))
    }

    override def put[T: Decoder](path: String,
                                 params: Map[String, String],
                                 headers: Map[String, String],
                                 body: Json): Future[Either[SpotifyError, T]] = {
      execute(Http(s"$ApiHost$path")
        .params(params)
        .headers(headers)
        .postData(body.toString())
        .method("PUT"))
    }

    override def delete[T: Decoder](path: String,
                                    params: Map[String, String],
                                    headers: Map[String, String]): Future[Either[SpotifyError, T]] = {
      execute(Http(s"$ApiHost$path")
        .method("DELETE")
        .params(params)
        .headers(headers))
    }

    override def post[T: Decoder](path: String,
                                  params: Map[String, String],
                                  headers: Map[String, String],
                                  body: Json): Future[Either[SpotifyError, T]] = {
      execute(Http(s"$ApiHost$path")
        .params(params)
        .headers(headers)
        .postData(body.toString()))
    }
  }


}
