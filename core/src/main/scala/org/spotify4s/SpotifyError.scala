package org.spotify4s

sealed trait SpotifyError

object SpotifyError {

  case class ApiError(spotifyError: models.Error) extends SpotifyError

  case class JsonDecodeError(error: io.circe.Error, payload: String) extends SpotifyError

}