package dev.esteban.movies.data.datasource.movies

import dev.esteban.movies.data.datasource.remote.model.NetworkMovieDetailResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkMoviesResponse
import dev.esteban.movies.data.datasource.remote.movies.MoviesApi
import dev.esteban.movies.data.datasource.remote.movies.MoviesDataSourceImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class MoviesDataSourceImplTest {
    private lateinit var dataSource: MoviesDataSourceImpl
    private val testDispatcher = StandardTestDispatcher()

    private val moviesApi: MoviesApi = mockk()

    @Before
    fun setup() {
        dataSource = MoviesDataSourceImpl(moviesApi)
    }

    @Test
    fun `nowPlaying should call api`() = runTest(testDispatcher) {
        val expectedResponse: NetworkMoviesResponse = mockk()
        coEvery { moviesApi.nowPlaying() } returns expectedResponse
        val result = dataSource.nowPlaying()

        Assert.assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { moviesApi.nowPlaying() }
    }

    @Test
    fun `nowPlaying should call api with error`() = runTest(testDispatcher) {
        val exception = IOException("Get now playing movies failed")

        coEvery { moviesApi.nowPlaying() } throws exception
        try {
            dataSource.nowPlaying()
            assert(false) { "Expected exception to be thrown" }
        } catch (e: Exception) {
            Assert.assertEquals("Get now playing movies failed", e.message)
        }

        coVerify(exactly = 1) { moviesApi.nowPlaying() }
    }

    @Test
    fun `search should call api`() = runTest(testDispatcher) {
        val expectedResponse: NetworkMoviesResponse = mockk()
        val query = "Shadow"
        coEvery { moviesApi.search(query) } returns expectedResponse
        val result = dataSource.search(query)

        Assert.assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { moviesApi.search(query) }
    }

    @Test
    fun `search should call api with error`() = runTest(testDispatcher) {
        val exception = IOException("Get now playing movies failed")
        val query = "Shadow"

        coEvery { moviesApi.search(query) } throws exception
        try {
            dataSource.search(query)
            assert(false) { "Expected exception to be thrown" }
        } catch (e: Exception) {
            Assert.assertEquals("Get now playing movies failed", e.message)
        }

        coVerify(exactly = 1) { moviesApi.search(query) }
    }

    @Test
    fun `trending should call api`() = runTest(testDispatcher) {
        val expectedResponse: NetworkMoviesResponse = mockk()
        coEvery { moviesApi.trending() } returns expectedResponse

        val result = dataSource.trending()

        Assert.assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { moviesApi.trending() }
    }

    @Test
    fun `trending should call api with error`() = runTest(testDispatcher) {
        val exception = IOException("Get trending movies failed")
        coEvery { moviesApi.trending() } throws exception

        try {
            dataSource.trending()
            assert(false) { "Expected exception to be thrown" }
        } catch (e: Exception) {
            Assert.assertEquals("Get trending movies failed", e.message)
        }

        coVerify(exactly = 1) { moviesApi.trending() }
    }

    @Test
    fun `upcoming should call api`() = runTest(testDispatcher) {
        val expectedResponse: NetworkMoviesResponse = mockk()
        coEvery { moviesApi.upcoming() } returns expectedResponse

        val result = dataSource.upcoming()

        Assert.assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { moviesApi.upcoming() }
    }

    @Test
    fun `upcoming should call api with error`() = runTest(testDispatcher) {
        val exception = IOException("Get upcoming movies failed")
        coEvery { moviesApi.upcoming() } throws exception

        try {
            dataSource.upcoming()
            assert(false) { "Expected exception to be thrown" }
        } catch (e: Exception) {
            Assert.assertEquals("Get upcoming movies failed", e.message)
        }

        coVerify(exactly = 1) { moviesApi.upcoming() }
    }

    @Test
    fun `popular should call api`() = runTest(testDispatcher) {
        val expectedResponse: NetworkMoviesResponse = mockk()
        coEvery { moviesApi.popular() } returns expectedResponse

        val result = dataSource.popular()

        Assert.assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { moviesApi.popular() }
    }

    @Test
    fun `popular should call api with error`() = runTest(testDispatcher) {
        val exception = IOException("Get popular movies failed")
        coEvery { moviesApi.popular() } throws exception

        try {
            dataSource.popular()
            assert(false) { "Expected exception to be thrown" }
        } catch (e: Exception) {
            Assert.assertEquals("Get popular movies failed", e.message)
        }

        coVerify(exactly = 1) { moviesApi.popular() }
    }

    @Test
    fun `discover should call api`() = runTest(testDispatcher) {
        val expectedResponse: NetworkMoviesResponse = mockk()
        val genres = "28,12"
        coEvery { moviesApi.discover(genres = genres) } returns expectedResponse

        val result = dataSource.discover(genres)

        Assert.assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { moviesApi.discover(genres = genres) }
    }

    @Test
    fun `discover should call api with error`() = runTest(testDispatcher) {
        val exception = IOException("Discover movies failed")
        val genres = "28,12"
        coEvery { moviesApi.discover(genres = genres) } throws exception

        try {
            dataSource.discover(genres)
            assert(false) { "Expected exception to be thrown" }
        } catch (e: Exception) {
            Assert.assertEquals("Discover movies failed", e.message)
        }

        coVerify(exactly = 1) { moviesApi.discover(genres = genres) }
    }

    @Test
    fun `movieDetail should call api`() = runTest(testDispatcher) {
        val expectedResponse: NetworkMovieDetailResponse = mockk()
        val movieId = "123"
        coEvery { moviesApi.movieDetail(movieId) } returns expectedResponse

        val result = dataSource.movieDetail(movieId)

        Assert.assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { moviesApi.movieDetail(movieId) }
    }

    @Test
    fun `movieDetail should call api with error`() = runTest(testDispatcher) {
        val exception = IOException("Movie detail failed")
        val movieId = "123"
        coEvery { moviesApi.movieDetail(movieId) } throws exception

        try {
            dataSource.movieDetail(movieId)
            assert(false) { "Expected exception to be thrown" }
        } catch (e: Exception) {
            Assert.assertEquals("Movie detail failed", e.message)
        }

        coVerify(exactly = 1) { moviesApi.movieDetail(movieId) }
    }
}