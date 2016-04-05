package org.spotify4s.models

import java.nio.file.{Files, Paths}

import io.circe.generic.auto._
import cats.data.Xor
import io.circe.Decoder
import org.scalatest._

import scala.collection.convert.decorateAll._


class TestModels extends FlatSpec with Matchers {
  def loadExample(path: String): String = {
    val fullPath = Paths.get(s"spotify-web-api/specifications/raml/examples/$path")
    println(s"reading $fullPath")
    Files.readAllLines(fullPath).asScala.mkString("\n")
  }

  def decoder[T: Decoder]: Decoder[T] = Decoder[T].prepare(_.withFocus(org.spotify4s.util.json.camelCaseKeys).acursor)

  "a positive response from the playlist resource" should "successfully parse into a PlaylistSimple" in {
    val jsonStr = loadExample("responses/get-playlist.json")
    val playlistXor: Xor[io.circe.Error, PlaylistSimple] = io.circe.parser.decode[PlaylistSimple](jsonStr)(decoder)
    playlistXor shouldBe a [Xor.Right[_]]
  }

  "a positive response from the user playlist's endpoint" should "successfully parse into a Page[PlaylistSimple] " in {
    val jsonStr = loadExample("responses/get-user-playlists.json")
    val playlistsXor = io.circe.parser.decode[Page[PlaylistSimple]](jsonStr)(decoder)
    playlistsXor shouldBe a [Xor.Right[_]]
  }

  "a positive response from the playlist tracks resource" should "successfully parse into a Page[PlaylistTrack]" in {
    val jsonStr = loadExample("responses/get-playlist-tracks.json")
    val playlistTracksXor = io.circe.parser.decode[Page[PlaylistTrack]](jsonStr)(decoder)
    playlistTracksXor shouldBe a [Xor.Right[_]]
  }

}
