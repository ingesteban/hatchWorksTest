package dev.esteban.movies.domain.repository

import dev.esteban.movies.domain.model.GenreModel
import dev.esteban.network.ResponseState
import kotlinx.coroutines.flow.Flow

interface GenresRepository {
    suspend fun genreList(): Flow<ResponseState<List<GenreModel>>>
}
