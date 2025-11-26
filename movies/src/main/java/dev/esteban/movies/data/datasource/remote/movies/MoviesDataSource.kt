package dev.esteban.movies.data.datasource.remote.movies

import dev.esteban.movies.data.datasource.remote.model.NetworkMovieDetailResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkMoviesResponse

/**
 * Contract used to expose the movies list services
 * This interface allow that the above layer(Repository) do not know about
 * the operation of network operation
 **/
interface MoviesDataSource {
    suspend fun nowPlaying(): NetworkMoviesResponse

    suspend fun search(query: String): NetworkMoviesResponse

    suspend fun trending(): NetworkMoviesResponse

    suspend fun upcoming(): NetworkMoviesResponse

    suspend fun popular(): NetworkMoviesResponse

    suspend fun discover(genres: String): NetworkMoviesResponse

    suspend fun movieDetail(movieId: String): NetworkMovieDetailResponse
}
