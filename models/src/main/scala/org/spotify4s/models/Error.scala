package org.spotify4s.models


/**
  * @param status  The HTTP status code (also returned in the response header; see [Response Status Codes](https://developer.spotify.com/web-api/user- guide/#response-status-codes) for more information).
  * @param message A short description of the cause of the error.
  **/
case class Error(status: Int, message: String)

