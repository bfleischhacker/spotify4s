package org.spotify4s.models


/**
  * @param text   The copyright text for this album.
  * @param `type` The type of copyright: C = the copyright, P = the sound recording (performance) copyright.
  **/
case class Copyright(text: String, `type`: Copyright.Type)

object Copyright {

  sealed abstract class Type(val identifier: String)

  object Type {

    object C extends Type("C")

    object P extends Type("P")

  }

}
