package org.spotify4s.models

import org.spotify4s.util.{Enumerated, Identifiable}


/**
  * @param text   The copyright text for this album.
  * @param `type` The type of copyright: C = the copyright, P = the sound recording (performance) copyright.
  **/
case class Copyright(text: String, `type`: Copyright.Type)

object Copyright {

  sealed abstract class Type(val identifier: String) extends Identifiable

  object Type extends Enumerated[Type] {
    override val all: Seq[Type] = Seq(C, P)

    object C extends Type("C")

    object P extends Type("P")

  }

}
