package petros.efthymiou.groovy.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import petros.efthymiou.groovy.R


class PlayListFragment : Fragment() {
    //PLAYLIST VIEWMODEL EXPONDRA CIERTA INFORMACION A NUESTRO MODEL
    lateinit var  viewModel: PlaylistViewModel
    lateinit var  viewModelFactory: PlaylistViewModelFactory

    private val service = PlaylistService(object : PlaylistAPI{
        override suspend fun fetchAllPlaylists(): List<Playlist> {
            TODO("Not yet implemented")
        }
    })
    private val repository = PlaylistRepository(service)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_play_list, container, false)

        setupViewModel()

        viewModel.playlist.observe(this as LifecycleOwner) { playlists ->
            if (playlists.getOrNull() != null)
                setupList(view, playlists.getOrNull()!!)
            else {
                //TODO
            }
        }
        return view
    }

    private fun setupList(view: View?, playlists: List<Playlist>) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyPlaylistRecyclerViewAdapter(playlists)
        }
    }

    private fun setupViewModel() {
        viewModelFactory = PlaylistViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaylistViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                PlayListFragment().apply {}
    }
}