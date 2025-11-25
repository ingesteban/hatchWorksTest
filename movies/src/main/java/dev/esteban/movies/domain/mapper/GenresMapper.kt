package dev.esteban.movies.domain.mapper

import dev.esteban.movies.data.datasource.remote.model.NetworkGenreResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkGenresResponse
import dev.esteban.movies.domain.model.GenreModel
import dev.esteban.network.Mapper
import dev.esteban.network.ResponseState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenresMapper
    @Inject
    constructor() : Mapper<NetworkGenresResponse, ResponseState<List<GenreModel>>> {
        override fun apply(input: NetworkGenresResponse): ResponseState<List<GenreModel>> =
            ResponseState.Success(input.genres.map { mapGenre(it) })

        private fun mapGenre(input: NetworkGenreResponse): GenreModel =
            GenreModel(
                id = input.id,
                name = input.name,
            )
    }
