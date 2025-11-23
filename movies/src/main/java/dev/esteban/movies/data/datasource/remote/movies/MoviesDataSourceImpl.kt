package dev.esteban.movies.data.datasource.remote.movies

import dev.esteban.network.RetrofitBuilder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesDataSourceImpl @Inject constructor(retrofitBuilder: RetrofitBuilder) :
    MoviesDataSource {
    private val networkApi = retrofitBuilder.create<MoviesApi>()

    override suspend fun nowPlaying() = networkApi.nowPlaying()
}
