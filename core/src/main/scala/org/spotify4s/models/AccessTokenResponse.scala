package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.syntax._
import io.circe.{Decoder, Encoder, Json}

/**
  * @param accessToken  An access token that can be provided in subsequent calls, for example to Spotify Web API services.
  * @param expiresIn    The time period (in seconds) for which the access token is valid.
  * @param refreshToken A token that can be sent to the Spotify Accounts service in place of an authorization code. (When the access code expires, send a POST request to the Accounts service /api/token endpoint, but use this code in place of an authorization code. A new access token will be returned. A new refresh token might be returned too.)
  */
case class AccessTokenResponse(accessToken: String, expiresIn: Int, refreshToken: String)

object AccessTokenResponse {
  implicit val decoder: Decoder[AccessTokenResponse] = {
    (Decoder.instance(_.get[String]("access_token")) |@|
      Decoder.instance(_.get[Int]("expires_in")) |@|
      Decoder.instance(_.get[String]("refresh_token"))).map(AccessTokenResponse.apply)
  }

  implicit val encoder: Encoder[AccessTokenResponse] = {
    Encoder.instance[AccessTokenResponse](accessTokenResponse => Json.obj(
      "access_token" -> accessTokenResponse.accessToken.asJson,
      "expires_in" -> accessTokenResponse.expiresIn.asJson,
      "refresh_token" -> accessTokenResponse.refreshToken.asJson
    ))
  }
}
