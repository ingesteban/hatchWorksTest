package dev.esteban.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.esteban.movies.domain.repository.GenresRepository
import dev.esteban.movies.domain.repository.MoviesRepository
import dev.esteban.movies.presentation.model.HomeUIState
import dev.esteban.network.ResponseState
import kotlinx.coroutines.Deferred
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

    private val homeMoviesMutableStateFlow: MutableStateFlow<HomeUIState> =
        MutableStateFlow(HomeUIState())
    val homeMoviesStateFlow: StateFlow<HomeUIState> = homeMoviesMutableStateFlow.asStateFlow()

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
}
