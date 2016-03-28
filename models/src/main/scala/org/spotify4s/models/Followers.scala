package org.spotify4s.models


/**
  * Information about the followers of an item.
  *
  * @param href  A link to the Web API endpoint providing full details of the followers; null if not available. Please note that this will always be set to `null`, as the Web API does not support it at the moment.
  * @param total The total number of followers.
  **/
case class Followers(href: String, total: Int)

