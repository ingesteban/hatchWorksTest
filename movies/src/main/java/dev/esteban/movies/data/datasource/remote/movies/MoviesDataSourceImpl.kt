package dev.esteban.movies.data.datasource.remote.movies

import dev.esteban.network.RetrofitBuilder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesDataSourceImpl @Inject constructor(retrofitBuilder: RetrofitBuilder) :
    MoviesDataSource {
    private val networkApi = retrofitBuilder.create<MoviesApi>()

    override suspend fun nowPlaying() = networkApi.nowPlaying()

    override suspend fun search(query: String) = networkApi.search(query)

    override suspend fun trending() = networkApi.trending()

    override suspend fun upcoming() = networkApi.upcoming()

    override suspend fun popular() = networkApi.popular()

    override suspend fun discover(genres: String) = networkApi.discover(genres = genres)

    override suspend fun movieDetail(movieId: String) = networkApi.movieDetail(movieId = movieId)
}
