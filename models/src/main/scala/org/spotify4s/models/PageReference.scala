package org.spotify4s.models


/**
  * A reference to a paged href
  *
  * @param href  A link to the Web API endpoint returning the full result of the request.
  * @param total The total number of items available to return.
  **/
case class PageReference(href: String, total: Int)

