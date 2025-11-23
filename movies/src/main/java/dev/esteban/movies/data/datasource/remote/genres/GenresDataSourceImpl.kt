package dev.esteban.movies.data.datasource.remote.genres

import dev.esteban.network.RetrofitBuilder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenresDataSourceImpl @Inject constructor(retrofitBuilder: RetrofitBuilder) :
    GenresDataSource {
    private val networkApi = retrofitBuilder.create<GenresApi>()

    override suspend fun genreList() = networkApi.genreList()
}
