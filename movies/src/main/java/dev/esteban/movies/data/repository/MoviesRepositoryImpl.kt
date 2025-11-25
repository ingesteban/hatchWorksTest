package dev.esteban.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.esteban.movies.data.datasource.remote.model.NetworkMoviesResponse
import dev.esteban.movies.data.datasource.remote.movies.MoviesDataSource
import dev.esteban.movies.data.datasource.remote.movies.paging.MoviesPagingSourceFactory
import dev.esteban.movies.util.MoviesEndpointType
import dev.esteban.movies.domain.mapper.MovieDetailMapper
import dev.esteban.movies.domain.mapper.MovieMapper
import dev.esteban.movies.domain.mapper.MoviesMapper
import dev.esteban.movies.domain.model.MovieDetailModel
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.movies.domain.repository.MoviesRepository
import dev.esteban.network.AppDispatchers.IO
import dev.esteban.network.Dispatcher
import dev.esteban.network.ResponseState
import dev.esteban.network.error.NetworkErrorMapper
import dev.esteban.network.mapper
import dev.esteban.network.onError
import dev.esteban.network.startFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val moviesPagingSourceFactory: MoviesPagingSourceFactory,
    private val moviesDataSource: MoviesDataSource,
    private val moviesMapper: MoviesMapper,
    private val movieDetailMapper: MovieDetailMapper,
    private val movieMapper: MovieMapper,
) : MoviesRepository {

    override fun nowPlaying(): Flow<ResponseState<List<MovieModel>>> =
        fetchMovies { moviesDataSource.nowPlaying() }

    override fun trending(): Flow<ResponseState<List<MovieModel>>> =
        fetchMovies { moviesDataSource.trending() }

    override fun upcoming(): Flow<ResponseState<List<MovieModel>>> =
        fetchMovies { moviesDataSource.upcoming() }

    override fun popular(): Flow<ResponseState<List<MovieModel>>> =
        fetchMovies { moviesDataSource.popular() }

    override fun search(query: String): Flow<ResponseState<List<MovieModel>>> =
        fetchMovies { moviesDataSource.search(query) }
            .startFlow(ResponseState.Loading)

    override fun discover(genres: String): Flow<ResponseState<List<MovieModel>>> =
        fetchMovies { moviesDataSource.discover(genres = genres) }
            .startFlow(ResponseState.Loading)

    override suspend fun movieDetail(movieId: String): Flow<ResponseState<MovieDetailModel>> = flow {
        emit(moviesDataSource.movieDetail(movieId))
    }.mapper(movieDetailMapper)
        .onError(NetworkErrorMapper())
        .flowOn(ioDispatcher)
        .startFlow(ResponseState.Loading)

    private fun fetchMovies(
        serviceCall: suspend () -> NetworkMoviesResponse
    ): Flow<ResponseState<List<MovieModel>>> = flow {
        delay(3000)
        emit(serviceCall())
    }.mapper(moviesMapper)
        .onError(NetworkErrorMapper())
        .flowOn(ioDispatcher)

    override fun getMoviesPagingByType(
        type: MoviesEndpointType,
        genres: String?
    ): Flow<PagingData<MovieModel>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            moviesPagingSourceFactory.create(type, genres)
        }
    ).flow
        .mapper(movieMapper)

}
