package org.spotify4s

import io.circe.Decoder
import org.spotify4s.models.{AccessTokenResponse, RefreshTokenResponse}
import org.spotify4s.resources.AbstractAccountResource

import scala.concurrent.{ExecutionContext, Future}
import scalaj.http.{Http, HttpRequest}

class SpotifyAccounts(clientId: String,
                      clientSecret: String)
                     (implicit executionContext: ExecutionContext) extends AbstractAccountResource {
  override val accounts: Accounts = new Accounts {

    private val AccountsHost = "https://accounts.spotify.com"

    def execute[T: Decoder](request: HttpRequest): Future[Either[SpotifyError, T]] = {
      Future(HttpUtil.execute(request))
    }

    override def requestTokens(code: String,
                               redirect_uri: String): Future[Either[SpotifyError, AccessTokenResponse]] = {
      execute[AccessTokenResponse](Http(s"$AccountsHost/api/token")
        .auth(clientId, clientSecret)
        .postForm(Seq(
          "grant_type" -> "authorization_code",
          "code" -> code,
          "redirect_uri" -> redirect_uri)))
    }

    override def refreshToken(refreshToken: String): Future[Either[SpotifyError, RefreshTokenResponse]] = {
      execute[RefreshTokenResponse](Http(s"$AccountsHost/api/token")
        .auth(clientId, clientSecret)
        .postForm(Seq(
          "grant_type" -> "refresh_token",
          "refresh_token" -> refreshToken)))
    }
  }
}
