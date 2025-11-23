package dev.esteban.movies.presentation.model

import dev.esteban.movies.domain.model.GenreModel
import dev.esteban.movies.domain.model.MovieModel

data class HomeUIState(
    val isLoading: Boolean = false,
    val trending: List<MovieModel>? = null,
    val popular: List<MovieModel>? = null,
    val upcoming: List<MovieModel>? = null,
    val nowPlaying: List<MovieModel>? = null,
    val genres: List<GenreModel>? = null
)
