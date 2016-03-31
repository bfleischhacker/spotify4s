package org.spotify4s.resources

import org.spotify4s.SpotifyError
import org.spotify4s.models.{AccessTokenResponse, RefreshTokenResponse}

import scala.concurrent.Future

/**
  * Created by bfly2000 on 3/29/16.
  */
trait AbstractAccountResource {
  val accounts: Accounts

  trait Accounts {
    /**
      * @param code         The authorization code returned from the initial request to the Account's /authorize endpoint.
      * @param redirect_uri This parameter is used for validation only (there is no actual redirection). The value of this parameter must exactly match the value of redirect_uri supplied when requesting the authorization code.
      * @return The
      */
    def requestTokens(code: String,
                      redirect_uri: String): Future[Either[SpotifyError, AccessTokenResponse]]

    def refreshToken(refreshToken: String): Future[Either[SpotifyError, RefreshTokenResponse]]
  }

}
