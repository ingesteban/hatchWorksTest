package dev.esteban.movies.data.datasource.remote.genres

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenresDataSourceImpl
    @Inject
    constructor(
        val genresApi: GenresApi,
    ) : GenresDataSource {
        override suspend fun genreList() = genresApi.genreList()
    }
