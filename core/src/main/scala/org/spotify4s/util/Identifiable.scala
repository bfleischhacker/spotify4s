package org.spotify4s.util

trait Identifiable {
  val identifier: String

  override def toString: String = identifier
}
