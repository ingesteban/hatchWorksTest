package dev.esteban.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.movies.domain.repository.GenresRepository
import dev.esteban.movies.domain.repository.MoviesRepository
import dev.esteban.network.ResponseState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {
    private var searchJob: Job? = null

    private val searchedMoviesMutableStateFlow: MutableStateFlow<ResponseState<List<MovieModel>>> =
        MutableStateFlow(ResponseState.Idle)
    val searchedMoviesStateFlow: StateFlow<ResponseState<List<MovieModel>>> =
        searchedMoviesMutableStateFlow.asStateFlow()

    fun searchMovies(query: String) {
        searchJob?.cancel()
        if (query.isBlank()) {
            searchedMoviesMutableStateFlow.value = ResponseState.Idle
            return
        }
        searchJob = viewModelScope.launch {
            moviesRepository.search(query)
                .collect { response ->
                    when (response) {
                        is ResponseState.Idle -> {
                            // NO_OP
                        }

                        is ResponseState.Loading -> searchedMoviesMutableStateFlow.emit(
                            ResponseState.Loading
                        )

                        is ResponseState.Success -> searchedMoviesMutableStateFlow.emit(
                            response
                        )

                        is ResponseState.Error -> searchedMoviesMutableStateFlow.emit(
                            response
                        )
                    }
                }
        }
    }
}
