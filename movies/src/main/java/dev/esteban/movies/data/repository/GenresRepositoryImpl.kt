package dev.esteban.movies.data.repository

import dev.esteban.movies.data.datasource.remote.genres.GenresDataSource
import dev.esteban.movies.domain.mapper.GenresMapper
import dev.esteban.movies.domain.model.GenreModel
import dev.esteban.movies.domain.repository.GenresRepository
import dev.esteban.network.AppDispatchers.IO
import dev.esteban.network.Dispatcher
import dev.esteban.network.ResponseState
import dev.esteban.network.error.NetworkErrorMapper
import dev.esteban.network.mapper
import dev.esteban.network.onError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenresRepositoryImpl
    @Inject
    constructor(
        @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
        private val genresMapper: GenresMapper,
        private val dataSource: GenresDataSource,
    ) : GenresRepository {
        override suspend fun genreList(): Flow<ResponseState<List<GenreModel>>> =
            flow {
                emit(dataSource.genreList())
            }.mapper(genresMapper)
                .onError(NetworkErrorMapper())
                .flowOn(ioDispatcher)
    }
