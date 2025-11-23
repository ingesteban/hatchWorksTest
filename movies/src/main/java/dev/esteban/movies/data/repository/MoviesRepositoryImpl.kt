package dev.esteban.movies.data.repository

import dev.esteban.movies.data.datasource.remote.movies.MoviesDataSource
import dev.esteban.movies.domain.mapper.MoviesMapper
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.movies.domain.repository.MoviesRepository
import dev.esteban.network.AppDispatchers.IO
import dev.esteban.network.Dispatcher
import dev.esteban.network.error.NetworkErrorMapper
import dev.esteban.network.ResponseState
import dev.esteban.network.mapper
import dev.esteban.network.onError
import dev.esteban.network.startFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val moviesDataSource: MoviesDataSource,
    private val moviesMapper: MoviesMapper,
) : MoviesRepository {
    override suspend fun nowPlaying(): Flow<ResponseState<List<MovieModel>>> = flow {
        emit(moviesDataSource.nowPlaying())
    }.mapper(moviesMapper)
        .onError(NetworkErrorMapper())
        .flowOn(ioDispatcher)
        .startFlow(ResponseState.Loading)
}
