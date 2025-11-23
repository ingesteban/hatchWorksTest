package dev.esteban.movies.domain.repository

import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.network.ResponseState
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun nowPlaying(): Flow<ResponseState<List<MovieModel>>>
}
