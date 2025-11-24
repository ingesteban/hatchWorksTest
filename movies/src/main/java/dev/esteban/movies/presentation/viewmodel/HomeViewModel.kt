package dev.esteban.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.esteban.movies.domain.model.MovieDetailModel
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.movies.domain.repository.GenresRepository
import dev.esteban.movies.domain.repository.MoviesRepository
import dev.esteban.movies.presentation.model.HomeUIState
import dev.esteban.network.ResponseState
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val genresRepository: GenresRepository,
) : ViewModel() {

    private var searchJob: Job? = null

    private val homeMoviesMutableStateFlow: MutableStateFlow<HomeUIState> =
        MutableStateFlow(HomeUIState())
    val homeMoviesStateFlow: StateFlow<HomeUIState> = homeMoviesMutableStateFlow.asStateFlow()

    private val moviesByGenreMutableStateFlow: MutableStateFlow<ResponseState<List<MovieModel>>> =
        MutableStateFlow(ResponseState.Idle)
    val moviesByGenreStateFlow: StateFlow<ResponseState<List<MovieModel>>> =
        moviesByGenreMutableStateFlow.asStateFlow()

    private val searchedMoviesMutableStateFlow: MutableStateFlow<ResponseState<List<MovieModel>>> =
        MutableStateFlow(ResponseState.Idle)
    val searchedMoviesStateFlow: StateFlow<ResponseState<List<MovieModel>>> =
        searchedMoviesMutableStateFlow.asStateFlow()

    private val movieDetailMutableStateFlow: MutableStateFlow<ResponseState<MovieDetailModel>> =
        MutableStateFlow(ResponseState.Idle)
    val movieDetailStateFlow: StateFlow<ResponseState<MovieDetailModel>> =
        movieDetailMutableStateFlow.asStateFlow()

    fun loadMovies() {
        viewModelScope.launch {
            val trendingDeferred = async { moviesRepository.trending().first() }
            val popularDeferred = async { moviesRepository.popular().first() }
            val upcomingDeferred = async { moviesRepository.upcoming().first() }
            val nowPlayingDeferred = async { moviesRepository.nowPlaying().first() }
            val genreListDeferred = async { genresRepository.genreList().first() }

            handleDeferredResult(trendingDeferred) { movies, currentState ->
                currentState.copy(trending = movies)
            }
            handleDeferredResult(popularDeferred) { movies, currentState ->
                currentState.copy(popular = movies)
            }
            handleDeferredResult(upcomingDeferred) { movies, currentState ->
                currentState.copy(upcoming = movies)
            }
            handleDeferredResult(nowPlayingDeferred) { movies, currentState ->
                currentState.copy(nowPlaying = movies)
            }
            handleDeferredResult(genreListDeferred) { genres, currentState ->
                currentState.copy(genres = genres)
            }
        }
    }

    private suspend fun <T> handleDeferredResult(
        deferred: Deferred<ResponseState<T>>,
        stateUpdater: (ResponseState<T>, HomeUIState) -> HomeUIState
    ) {
        try {
            homeMoviesMutableStateFlow.value =
                stateUpdater(deferred.await(), homeMoviesStateFlow.value)
        } catch (_: Exception) {
            homeMoviesMutableStateFlow.value =
                stateUpdater(ResponseState.Error(), homeMoviesStateFlow.value)
        }
    }

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

    fun discoverByGenre(genres: String) {
        viewModelScope.launch {
            moviesRepository.discover(genres)
                .collect { response ->
                    when (response) {
                        is ResponseState.Idle -> {
                            // NO_OP
                        }

                        is ResponseState.Loading -> moviesByGenreMutableStateFlow.emit(
                            ResponseState.Loading
                        )

                        is ResponseState.Success -> moviesByGenreMutableStateFlow.emit(
                            response
                        )

                        is ResponseState.Error -> moviesByGenreMutableStateFlow.emit(
                            response
                        )
                    }
                }
        }
    }

    fun movieDetail(movieId: Int) {
        viewModelScope.launch {
            moviesRepository.movieDetail(movieId)
                .collect { response ->
                    when (response) {
                        is ResponseState.Idle -> {
                            // NO_OP
                        }

                        is ResponseState.Loading -> movieDetailMutableStateFlow.emit(
                            ResponseState.Loading
                        )

                        is ResponseState.Success -> movieDetailMutableStateFlow.emit(
                            response
                        )

                        is ResponseState.Error -> movieDetailMutableStateFlow.emit(
                            response
                        )
                    }
                }
        }
    }

    private fun ResponseState<List<MovieModel>>.extractData(): List<MovieModel> {
        return if (this is ResponseState.Success) this.response else emptyList()
    }
}