package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlayListServiceShould:  BaseUnitTest() {
    private lateinit var service: PlaylistService
    private val api : PlaylistAPI = mock()
    private val playlist : List<Playlist> = mock()

    @Test
    fun fetchPlaylistFromAPI() = runBlockingTest {
        service = PlaylistService(api)
        service.fetchPlaylist().first()

        verify(api, times(1)).fetchAllPlaylists()

    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest{

        mockSuccessfulCase()

        assertEquals(Result.success(playlist), service.fetchPlaylist().first())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        mockErrorCase()

        assertEquals("Something went wrong",
            service.fetchPlaylist().first().exceptionOrNull()?.message)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchAllPlaylists()).thenReturn(playlist)

        service = PlaylistService(api)
    }

    private suspend fun mockErrorCase() {
        whenever(api.fetchAllPlaylists()).thenThrow(RuntimeException("Damn backend developer"))

        service = PlaylistService(api)
    }


}