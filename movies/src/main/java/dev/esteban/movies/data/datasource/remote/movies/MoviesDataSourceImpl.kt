package dev.esteban.movies.data.datasource.remote.movies

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesDataSourceImpl @Inject constructor(val moviesApi: MoviesApi) : MoviesDataSource {

    override suspend fun nowPlaying() = moviesApi.nowPlaying()

    override suspend fun search(query: String) = moviesApi.search(query)

    override suspend fun trending() = moviesApi.trending()

    override suspend fun upcoming() = moviesApi.upcoming()

    override suspend fun popular() = moviesApi.popular()

    override suspend fun discover(genres: String) = moviesApi.discover(genres = genres)

    override suspend fun movieDetail(movieId: String) = moviesApi.movieDetail(movieId = movieId)
}
