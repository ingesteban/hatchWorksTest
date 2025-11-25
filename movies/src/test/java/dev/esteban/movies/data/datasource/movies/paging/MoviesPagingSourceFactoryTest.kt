package dev.esteban.movies.data.datasource.movies.paging

import androidx.paging.PagingSource
import dev.esteban.movies.data.datasource.remote.model.NetworkMovieResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkMoviesResponse
import dev.esteban.movies.data.datasource.remote.movies.MoviesApi
import dev.esteban.movies.data.datasource.remote.movies.paging.MoviesPagingSourceFactory
import dev.esteban.movies.util.MoviesEndpointType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MoviesPagingSourceFactoryTest {
    private lateinit var moviesApi: MoviesApi
    private lateinit var networkMovieResponse: NetworkMovieResponse
    private lateinit var factory: MoviesPagingSourceFactory

    @Before
    fun setup() {
        moviesApi = mockk(relaxed = true)
        networkMovieResponse = mockk(relaxed = true)
        factory = MoviesPagingSourceFactory(moviesApi)
    }

    private fun mockMoviesResponse(
        page: Int,
        totalPages: Int = 10,
    ) = NetworkMoviesResponse(
        page = page,
        totalPages = totalPages,
        totalResults = 300,
        results = listOf(networkMovieResponse),
    )

    private suspend fun PagingSource<Int, NetworkMovieResponse>.loadPage(key: Int) =
        this.load(
            PagingSource.LoadParams.Refresh(
                key = key,
                loadSize = 20,
                placeholdersEnabled = false,
            ),
        )

    @Test
    fun `factory creates pagingSource that calls trending`() =
        runTest {
            val pagingSource = factory.create(MoviesEndpointType.TRENDING)
            val response = mockMoviesResponse(page = 1)

            coEvery { moviesApi.trending(1) } returns response

            val result = pagingSource.loadPage(1)

            coVerify(exactly = 1) { moviesApi.trending(1) }
            assertTrue(result is PagingSource.LoadResult.Page)
        }

    @Test
    fun `factory creates pagingSource that calls upcoming`() =
        runTest {
            val pagingSource = factory.create(MoviesEndpointType.UPCOMING)
            val response = mockMoviesResponse(page = 2)

            coEvery { moviesApi.upcoming(2) } returns response

            val result = pagingSource.loadPage(2)

            coVerify(exactly = 1) { moviesApi.upcoming(2) }
            assertTrue(result is PagingSource.LoadResult.Page)
        }

    @Test
    fun `factory creates pagingSource that calls discover with genres`() =
        runTest {
            val genres = "28,12"
            val pagingSource = factory.create(MoviesEndpointType.GENRE, genres)

            val response = mockMoviesResponse(page = 3)

            coEvery { moviesApi.discover(page = 3, genres = genres) } returns response

            val result = pagingSource.loadPage(3)

            coVerify(exactly = 1) { moviesApi.discover(3, genres) }
            assertTrue(result is PagingSource.LoadResult.Page)

            val page = result as PagingSource.LoadResult.Page
            assertEquals(response.results, page.data)
            assertEquals(2, page.prevKey)
            assertEquals(4, page.nextKey)
        }

    @Test
    fun `factory creates pagingSource that calls discover with empty genres when null`() =
        runTest {
            val pagingSource = factory.create(MoviesEndpointType.GENRE, null)

            val response = mockMoviesResponse(page = 1)

            coEvery { moviesApi.discover(page = 1, genres = "") } returns response

            val result = pagingSource.loadPage(1)

            coVerify(exactly = 1) { moviesApi.discover(1, "") }
            assertTrue(result is PagingSource.LoadResult.Page)

            val page = result as PagingSource.LoadResult.Page
            assertEquals(response.results, page.data)
            assertNull(page.prevKey)
            assertEquals(2, page.nextKey)
        }

    @Test
    fun `factory defaults to nowPlaying for NOW_PLAYING`() =
        runTest {
            val pagingSource = factory.create(MoviesEndpointType.NOW_PLAYING)

            val response = mockMoviesResponse(page = 1)

            coEvery { moviesApi.nowPlaying(1) } returns response

            val result = pagingSource.loadPage(1)

            coVerify(exactly = 1) { moviesApi.nowPlaying(1) }
            assertTrue(result is PagingSource.LoadResult.Page)
        }

    @Test
    fun `factory defaults to nowPlaying for unknown types`() =
        runTest {
            val pagingSource = factory.create(MoviesEndpointType.NOW_PLAYING)

            val response = mockMoviesResponse(page = 5)

            coEvery { moviesApi.nowPlaying(5) } returns response

            val result = pagingSource.loadPage(5)

            coVerify(exactly = 1) { moviesApi.nowPlaying(5) }
            assertTrue(result is PagingSource.LoadResult.Page)
        }
}
