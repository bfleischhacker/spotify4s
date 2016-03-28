package org.spotify4s.models


/**
  * @param height The image height in pixels. If unknown: `null` or not returned.
  * @param url    The source URL of the image.
  * @param width  The image width in pixels. If unknown: `null` or not returned.
  **/
case class Image(height: Int, url: String, width: Int)

