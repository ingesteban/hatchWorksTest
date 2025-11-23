package dev.esteban.hatchworkstest.screen.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.movies.domain.repository.MoviesRepository
import dev.esteban.network.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val nowPlayingMoviesMutableStateFlow: MutableStateFlow<ResponseState<List<MovieModel>>> =
        MutableStateFlow(ResponseState.Idle)
    val nowPlayingMoviesStateFlow: StateFlow<ResponseState<List<MovieModel>>> =
        nowPlayingMoviesMutableStateFlow.asStateFlow()

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            moviesRepository.nowPlaying()
                .collect { response ->
                    when (response) {
                        is ResponseState.Idle -> {
                            // NO_OP
                        }

                        is ResponseState.Loading -> nowPlayingMoviesMutableStateFlow.emit(
                            ResponseState.Loading
                        )

                        is ResponseState.Success -> nowPlayingMoviesMutableStateFlow.emit(
                            response
                        )

                        is ResponseState.Error -> nowPlayingMoviesMutableStateFlow.emit(
                            response
                        )
                    }
                }
        }
    }

}
