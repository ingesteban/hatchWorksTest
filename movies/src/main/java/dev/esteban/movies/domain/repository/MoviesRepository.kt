package dev.esteban.movies.domain.repository

import dev.esteban.movies.domain.model.MovieDetailModel
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.network.ResponseState
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun nowPlaying(): Flow<ResponseState<List<MovieModel>>>

    fun search(query: String): Flow<ResponseState<List<MovieModel>>>

    fun trending(): Flow<ResponseState<List<MovieModel>>>

    fun upcoming(): Flow<ResponseState<List<MovieModel>>>

    fun popular(): Flow<ResponseState<List<MovieModel>>>

    fun discover(genres: String): Flow<ResponseState<List<MovieModel>>>

    suspend fun movieDetail(movieId: Int): Flow<ResponseState<MovieDetailModel>>
}
