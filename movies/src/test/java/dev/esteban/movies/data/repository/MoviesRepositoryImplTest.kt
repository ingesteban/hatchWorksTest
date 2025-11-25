package dev.esteban.movies.data.repository

import dev.esteban.movies.data.datasource.remote.model.NetworkMovieDetailResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkMoviesResponse
import dev.esteban.movies.data.datasource.remote.movies.MoviesDataSource
import dev.esteban.movies.data.datasource.remote.movies.paging.MoviesPagingSourceFactory
import dev.esteban.movies.domain.mapper.MovieDetailMapper
import dev.esteban.movies.domain.mapper.MovieMapper
import dev.esteban.movies.domain.mapper.MoviesMapper
import dev.esteban.movies.domain.model.MovieDetailModel
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.network.ResponseState
import dev.esteban.network.error.ErrorType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesRepositoryImplTest {
    private lateinit var repository: MoviesRepositoryImpl

    private val moviesMapper: MoviesMapper = mockk(relaxed = true)
    private val movieDetailMapper: MovieDetailMapper = mockk(relaxed = true)
    private val movieMapper: MovieMapper = mockk(relaxed = true)
    private val moviesPagingSourceFactory: MoviesPagingSourceFactory = mockk(relaxed = true)

    private val dataSource: MoviesDataSource = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    private val sampleResponse = mockk<NetworkMoviesResponse>(relaxed = true)
    private val movieModelMockk = mockk<MovieModel>(relaxed = true)
    private val movieDetailModelMockk = mockk<MovieDetailModel>(relaxed = true)
    private val mappedMovies = ResponseState.Success(listOf(movieModelMockk))
    private val mappedDetail = ResponseState.Success(movieDetailModelMockk)

    @Before
    fun setup() {
        repository =
            MoviesRepositoryImpl(
                ioDispatcher = testDispatcher,
                moviesPagingSourceFactory = moviesPagingSourceFactory,
                moviesDataSource = dataSource,
                moviesMapper = moviesMapper,
                movieDetailMapper = movieDetailMapper,
                movieMapper = movieMapper,
            )
    }

    private suspend fun runListFlowTest(
        callStub: suspend () -> Flow<ResponseState<List<MovieModel>>>,
        dataSourceCall: suspend () -> NetworkMoviesResponse,
    ) {
        coEvery { dataSourceCall() } returns sampleResponse
        coEvery { moviesMapper.apply(sampleResponse) } returns mappedMovies

        val flow = callStub()

        assertEquals(mappedMovies, flow.last())
    }

    private suspend fun runListFlowErrorTest(
        callStub: suspend () -> Flow<ResponseState<List<MovieModel>>>,
        dataSourceCall: suspend () -> NetworkMoviesResponse,
    ) {
        coEvery { dataSourceCall() } throws IOException("Network error")

        val flow = callStub()
        val last = flow.last()

        assertTrue(last is ResponseState.Error)
        assertEquals(ErrorType.UNKNOWN, (last as ResponseState.Error).errorType)
    }

    @Test
    fun `nowPlaying emits Success`() =
        runTest {
            runListFlowTest(repository::nowPlaying, dataSource::nowPlaying)
        }

    @Test
    fun `nowPlaying emits Error on exception`() =
        runTest {
            runListFlowErrorTest(repository::nowPlaying, dataSource::nowPlaying)
        }

    @Test
    fun `trending emits Success`() =
        runTest {
            runListFlowTest(repository::trending, dataSource::trending)
        }

    @Test
    fun `trending emits Error on exception`() =
        runTest {
            runListFlowErrorTest(repository::trending, dataSource::trending)
        }

    @Test
    fun `upcoming emits Success`() =
        runTest {
            runListFlowTest(repository::upcoming, dataSource::upcoming)
        }

    @Test
    fun `upcoming emits Error on exception`() =
        runTest {
            runListFlowErrorTest(repository::upcoming, dataSource::upcoming)
        }

    @Test
    fun `popular emits Success`() =
        runTest {
            runListFlowTest(repository::popular, dataSource::popular)
        }

    @Test
    fun `popular emits Error on exception`() =
        runTest {
            runListFlowErrorTest(repository::popular, dataSource::popular)
        }

    @Test
    fun `search emits Loading then Success`() =
        runTest {
            val query = "Batman"

            coEvery { dataSource.search(query) } returns sampleResponse
            coEvery { moviesMapper.apply(sampleResponse) } returns mappedMovies

            val flow = repository.search(query)

            assertEquals(ResponseState.Loading, flow.first())
            assertEquals(mappedMovies, flow.last())
        }

    @Test
    fun `search emits Error on exception`() =
        runTest {
            val query = "Batman"
            coEvery { dataSource.search(query) } throws IOException("Error")

            val flow = repository.search(query)
            val last = flow.last()

            assertTrue(last is ResponseState.Error)
            assertEquals(ErrorType.UNKNOWN, (last as ResponseState.Error).errorType)
        }

    @Test
    fun `discover emits Loading then Success`() =
        runTest {
            val genres = "28,12"

            coEvery { dataSource.discover(genres) } returns sampleResponse
            coEvery { moviesMapper.apply(sampleResponse) } returns mappedMovies

            val flow = repository.discover(genres)

            assertEquals(ResponseState.Loading, flow.first())
            assertEquals(mappedMovies, flow.last())
        }

    @Test
    fun `discover emits Error on exception`() =
        runTest {
            val genres = "28,12"
            coEvery { dataSource.discover(genres) } throws IOException("Error")

            val flow = repository.discover(genres)
            val last = flow.last()

            assertTrue(last is ResponseState.Error)
            assertEquals(ErrorType.UNKNOWN, (last as ResponseState.Error).errorType)
        }

    @Test
    fun `movieDetail emits Loading then Success`() =
        runTest {
            val id = "123"
            val networkMovieResponseMockk: NetworkMovieDetailResponse = mockk()

            coEvery { dataSource.movieDetail(id) } returns networkMovieResponseMockk
            coEvery { movieDetailMapper.apply(any()) } returns mappedDetail

            val flow = repository.movieDetail(id)

            assertEquals(ResponseState.Loading, flow.first())
            assertEquals(mappedDetail, flow.last())
        }

    @Test
    fun `movieDetail emits Error on exception`() =
        runTest {
            val id = "999"

            coEvery { dataSource.movieDetail(id) } throws IOException("Error")

            val flow = repository.movieDetail(id)
            val last = flow.last()

            assertTrue(last is ResponseState.Error)
            assertEquals(ErrorType.UNKNOWN, (last as ResponseState.Error).errorType)
        }
}
