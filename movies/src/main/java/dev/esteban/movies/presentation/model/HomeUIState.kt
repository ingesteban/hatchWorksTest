package dev.esteban.movies.presentation.model

import dev.esteban.movies.domain.model.GenreModel
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.network.ResponseState

data class HomeUIState(
    val popular: ResponseState<List<MovieModel>> = ResponseState.Loading,
    val trending: ResponseState<List<MovieModel>> = ResponseState.Loading,
    val nowPlaying: ResponseState<List<MovieModel>> = ResponseState.Loading,
    val upcoming: ResponseState<List<MovieModel>> = ResponseState.Loading,
    val genres: ResponseState<List<GenreModel>> = ResponseState.Loading,
)
