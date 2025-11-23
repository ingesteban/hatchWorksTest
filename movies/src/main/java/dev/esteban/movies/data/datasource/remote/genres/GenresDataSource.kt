package dev.esteban.movies.data.datasource.remote.genres

import dev.esteban.movies.data.datasource.remote.model.NetworkGenresResponse

interface GenresDataSource {
    suspend fun genreList(): NetworkGenresResponse
}
