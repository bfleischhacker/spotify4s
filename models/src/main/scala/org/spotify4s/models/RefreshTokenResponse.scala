package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.syntax._
import io.circe.{Decoder, Encoder, Json}

/**
  * @param accessToken An access token that can be provided in subsequent calls, for example to Spotify Web API services.
  * @param expiresIn   The time period (in seconds) for which the access token is valid.
  */
case class RefreshTokenResponse(accessToken: String, expiresIn: Int)

object RefreshTokenResponse {
  implicit val decoder: Decoder[RefreshTokenResponse] = {
    (Decoder.instance(_.get[String]("access_token")) |@|
      Decoder.instance(_.get[Int]("expires_in"))).map(RefreshTokenResponse.apply)
  }

  implicit val encoder: Encoder[RefreshTokenResponse] = {
    Encoder.instance[RefreshTokenResponse](accessTokenResponse => Json.obj(
      "access_token" -> accessTokenResponse.accessToken.asJson,
      "expires_in" -> accessTokenResponse.expiresIn.asJson
    ))
  }
}
