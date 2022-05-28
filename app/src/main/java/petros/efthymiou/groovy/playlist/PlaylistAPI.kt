package petros.efthymiou.groovy.playlist

interface PlaylistAPI {
    suspend fun fetchAllPlaylists():List<Playlist>
}