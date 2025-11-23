package dev.esteban.movies.data.datasource.remote.movies

import dev.esteban.movies.ConstantsParameters.MOVIE_ID
import dev.esteban.movies.ConstantsParameters.PAGE
import dev.esteban.movies.ConstantsParameters.QUERY
import dev.esteban.movies.ConstantsParameters.WITH_GENRES
import dev.esteban.movies.ConstantsURL.DISCOVER
import dev.esteban.movies.ConstantsURL.MOVIE_DETAIL
import dev.esteban.movies.ConstantsURL.NOW_PLAYING
import dev.esteban.movies.ConstantsURL.POPULAR
import dev.esteban.movies.ConstantsURL.SEARCH
import dev.esteban.movies.ConstantsURL.TRENDING
import dev.esteban.movies.ConstantsURL.UPCOMING
import dev.esteban.movies.data.datasource.remote.model.NetworkMovieDetailResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET(NOW_PLAYING)
    suspend fun nowPlaying(
        @Query(PAGE) page: Int = 1,
    ): NetworkMoviesResponse

    @GET(SEARCH)
    suspend fun search(
        @Query(QUERY) query: String,
    ): NetworkMoviesResponse

    @GET(TRENDING)
    suspend fun trending(
        @Query(PAGE) page: Int = 1,
    ): NetworkMoviesResponse

    @GET(UPCOMING)
    suspend fun upcoming(
        @Query(PAGE) page: Int = 1,
    ): NetworkMoviesResponse

    @GET(POPULAR)
    suspend fun popular(
        @Query(PAGE) page: Int = 1,
    ): NetworkMoviesResponse

    @GET(DISCOVER)
    suspend fun discover(
        @Query(PAGE) page: Int = 1,
        @Query(WITH_GENRES) genres: String
    ): NetworkMoviesResponse

    @GET(MOVIE_DETAIL)
    suspend fun movieDetail(
        @Path(MOVIE_ID) movieId: Int
    ): NetworkMovieDetailResponse
}
