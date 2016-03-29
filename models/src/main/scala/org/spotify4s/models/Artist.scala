package org.spotify4s.models

import cats.syntax.cartesian._
import io.circe.{Decoder, Encoder, Json}


/**
  * @param externalUrls
  * @param href       A link to the Web API endpoint providing full details of the artist.
  * @param id
  * @param name       The name of the artist.
  * @param `type`     The object type: "artist"
  * @param uri
  * @param followers
  * @param genres     A list of the genres the artist is associated with. If not yet classified, the array is empty.
  * @param images
  * @param popularity The popularity of the artist. The value will be between 0 and 100, with 100 being the most popular. The artist's popularity is calculated from the popularity of all the artist's tracks.
  **/
case class Artist(externalUrls: ExternalUrl,
                  href: String,
                  id: SpotifyId,
                  name: String,
                  `type`: String,
                  uri: SpotifyUri,
                  followers: Followers,
                  genres: List[String],
                  images: List[Image],
                  popularity: Int)

object Artist {
  implicit val decoder: Decoder[Artist] = (Decoder.instance(_.get[ExternalUrl]("external_urls")) |@| Decoder
    .instance(_.get[String]("href")) |@| Decoder.instance(_.get[SpotifyId]("id")) |@| Decoder
    .instance(_.get[String]("name")) |@| Decoder.instance(_.get[String]("type")) |@| Decoder
    .instance(_.get[SpotifyUri]("uri")) |@| Decoder.instance(_.get[Followers]("followers")) |@| Decoder
    .instance(_.get[List[String]]("genres")) |@| Decoder.instance(_.get[List[Image]]("images")) |@| Decoder
    .instance(_.get[Int]("popularity"))).map(Artist.apply)

  implicit val encoder: Encoder[Artist] = Encoder
    .instance(artist => Json
      .obj("external_urls" -> Encoder[ExternalUrl].apply(artist.externalUrls),
        "href" -> Encoder[String].apply(artist.href),
        "id" -> Encoder[SpotifyId].apply(artist.id),
        "name" -> Encoder[String].apply(artist.name),
        "type" -> Encoder[String].apply(artist.`type`),
        "uri" -> Encoder[SpotifyUri].apply(artist.uri),
        "followers" -> Encoder[Followers].apply(artist.followers),
        "genres" -> Encoder[List[String]].apply(artist.genres),
        "images" -> Encoder[List[Image]].apply(artist.images),
        "popularity" -> Encoder[Int].apply(artist.popularity)))


}
