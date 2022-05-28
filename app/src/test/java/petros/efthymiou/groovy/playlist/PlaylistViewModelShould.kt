package petros.efthymiou.groovy.playlist

import org.junit.Test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.getValueForTest
import java.lang.RuntimeException

class PlaylistViewModelShould : BaseUnitTest(){

    //inicializamos lo que vamos a necesitar par hacer que compile la prueba
    private val repository: PlaylistRepository = mock()
    private val playlist = mock<List<Playlist>>()
    private val expected = Result.success(playlist)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlayListFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        //Asumimos que tienes un viewmodel y de la clase que metimos utilizas una clase que te va a yudar a probar
        viewModel.playlist.getValueForTest()
        //Ahora probamos, verifica el repositorio una vez yh obten la lista.
        verify(repository, times(1)).getPlaylists()
    }


    @Test
    fun emitsPlaylistFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        assertEquals(expected, viewModel.playlist.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() {
        runBlocking{
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(Result.failure<List<Playlist>>(exception))
                }
            )
        }
        val viewModel = PlaylistViewModel(repository)

        assertEquals(exception, viewModel.playlist.getValueForTest()!!.exceptionOrNull())
    }

    private fun mockSuccessfulCase(): PlaylistViewModel {
        //Aqui mando a llamar el hilo para correrlo
        runBlocking {
            //Cada vez que tengamos un metodo mock realizremos el comportamiento en cada una de las lista
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
            //El viewmodel es esl unico objeto que debe ser reeal, todo lo demas debera ser mock
        }
        return PlaylistViewModel(repository)
    }
}