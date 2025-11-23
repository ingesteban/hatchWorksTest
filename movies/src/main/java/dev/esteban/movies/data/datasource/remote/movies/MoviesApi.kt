package dev.esteban.movies.data.datasource.remote.movies

import dev.esteban.movies.data.datasource.remote.model.NetworkMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("movie/now_playing")
    suspend fun nowPlaying(
        @Query("page") page: Int = 1,
    ): NetworkMoviesResponse
}
