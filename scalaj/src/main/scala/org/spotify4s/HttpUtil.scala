package org.spotify4s

import cats.syntax.xor._
import io.circe.{Decoder, Json}

import scalaj.http.{HttpRequest, HttpResponse}

object HttpUtil {

  def execute[T: Decoder](request: HttpRequest): Either[SpotifyError, T] = {
    val response: HttpResponse[String] = request.asString

    if (response.is2xx) {
      if (response.body.isEmpty) {
        Json.Empty.as[T].leftMap(SpotifyError.JsonDecodeError(_, response.body)).toEither
      }
      else {
        io.circe.parser.decode[T](response.body).leftMap(SpotifyError.JsonDecodeError(_, response.body)).toEither
      }
    } else {
      io.circe.parser.decode[org.spotify4s.models.ErrorObject](response.body)
        .map(_.error)
        .fold(SpotifyError.JsonDecodeError(_, response.body).left[T], SpotifyError.ApiError(_).left[T]).toEither
    }
  }


}
