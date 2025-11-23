package dev.esteban.movies.data.datasource.remote.movies

import dev.esteban.movies.data.datasource.remote.model.NetworkMoviesResponse

interface MoviesDataSource {
    suspend fun nowPlaying(): NetworkMoviesResponse
}
