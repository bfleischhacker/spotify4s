package org.spotify4s.models

case class Page[T](href: String,
                   items: List[T],
                   limit: Int,
                   total: Int,
                   offset: Int,
                   next: Option[String],
                   previous: Option[String])

