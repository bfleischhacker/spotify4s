package org.spotify4s.models


/**
  * @param collaborative `true` if the owner allows other users to modify the playlist.
  * @param externalUrls
  * @param href          A link to the Web API endpoint providing full details of the playlist.
  * @param id
  * @param images        Images for the playlist. The array may be empty or contain up to three images. The images are returned by size in descending order. See [Working with Playlists](https://developer.spotify.com/web-api/working-with-playlists/). Note: If returned, the source URL for the image (url) is temporary and will expire in less than a day.
  * @param name          The name of the playlist.
  * @param owner         The user who owns the playlist.
  * @param public        The playlist's public/private status: `true` the playlist is public, `false` the playlist is private, `null` the playlist status is not relevant. For more about public/private status, see [Working with Playlists](https://developer.spotify.com/web-api/working-with-playlists/).
  * @param snapshotId    The version identifier for the current playlist. Can be supplied in other requests to target a specific playlist version: see [Remove tracks from a playlist](https://developer.spotify.com/web-api/remove-tracks-playlist/).
  * @param tracks        Information about the tracks of the playlist.
  * @param `type`        The object type: "playlist"
  * @param uri
  **/
case class PlaylistSimple(collaborative: Boolean,
                          externalUrls: ExternalUrl,
                          href: String,
                          id: SpotifyId,
                          images: List[Image],
                          name: String,
                          owner: UserPublic,
                          public: Boolean,
                          snapshotId: String,
                          tracks: PageReference,
                          `type`: String,
                          uri: SpotifyUri)

