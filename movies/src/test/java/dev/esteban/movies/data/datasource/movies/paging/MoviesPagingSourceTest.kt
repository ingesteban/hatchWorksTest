package dev.esteban.movies.data.datasource.movies.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.esteban.movies.data.datasource.remote.model.NetworkMovieResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkMoviesResponse
import dev.esteban.movies.data.datasource.remote.movies.paging.MoviesPagingSource
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class MoviesPagingSourceTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var pagingSource: MoviesPagingSource

    @Test
    fun `load returns Page on success first page`() =
        runTest(testDispatcher) {
            val expectedMovies = listOf<NetworkMovieResponse>(mockk(), mockk())
            val response =
                NetworkMoviesResponse(
                    page = 1,
                    results = expectedMovies,
                    totalPages = 3,
                    totalResults = 300,
                )

            pagingSource =
                MoviesPagingSource { page ->
                    assertEquals(1, page)
                    response
                }

            val result =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 20,
                        placeholdersEnabled = false,
                    ),
                )

            val page = result as PagingSource.LoadResult.Page
            assertEquals(expectedMovies, page.data)
            assertEquals(null, page.prevKey)
            assertEquals(2, page.nextKey)
        }

    @Test
    fun `load returns Page on success middle page`() =
        runTest(testDispatcher) {
            val expectedMovies = listOf<NetworkMovieResponse>(mockk())
            val response =
                NetworkMoviesResponse(
                    page = 2,
                    results = expectedMovies,
                    totalPages = 5,
                    totalResults = 300,
                )

            pagingSource =
                MoviesPagingSource { page ->
                    assertEquals(2, page)
                    response
                }

            val result =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 2,
                        loadSize = 20,
                        placeholdersEnabled = false,
                    ),
                )

            val page = result as PagingSource.LoadResult.Page
            assertEquals(expectedMovies, page.data)
            assertEquals(1, page.prevKey)
            assertEquals(3, page.nextKey)
        }

    @Test
    fun `load returns Page with nextKey null when last page`() =
        runTest(testDispatcher) {
            val expectedMovies = listOf<NetworkMovieResponse>(mockk())
            val response =
                NetworkMoviesResponse(
                    page = 5,
                    results = expectedMovies,
                    totalPages = 5,
                    totalResults = 300,
                )

            pagingSource =
                MoviesPagingSource { page ->
                    assertEquals(5, page)
                    response
                }

            val result =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 5,
                        loadSize = 20,
                        placeholdersEnabled = false,
                    ),
                )

            val page = result as PagingSource.LoadResult.Page
            assertEquals(expectedMovies, page.data)
            assertEquals(4, page.prevKey)
            assertEquals(null, page.nextKey)
        }

    @Test
    fun `load returns Error on exception`() =
        runTest(testDispatcher) {
            val exception = IOException("Network failure")

            pagingSource =
                MoviesPagingSource { _ ->
                    throw exception
                }

            val result =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 20,
                        placeholdersEnabled = false,
                    ),
                )

            val error = result as PagingSource.LoadResult.Error
            assertEquals("Network failure", error.throwable.message)
        }

    @Test
    fun `getRefreshKey returns correct key when prevKey exists`() {
        val page =
            PagingSource.LoadResult.Page(
                data = listOf<NetworkMovieResponse>(mockk()),
                prevKey = 1,
                nextKey = 3,
            )

        val state =
            PagingState(
                pages = listOf(page),
                anchorPosition = 5,
                config = PagingConfig(pageSize = 20),
                leadingPlaceholderCount = 0,
            )

        pagingSource = MoviesPagingSource { mockk() }

        val key = pagingSource.getRefreshKey(state)

        assertEquals(2, key)
    }

    @Test
    fun `getRefreshKey returns correct key when only nextKey exists`() {
        val page =
            PagingSource.LoadResult.Page(
                data = listOf<NetworkMovieResponse>(mockk()),
                prevKey = null,
                nextKey = 2,
            )

        val state =
            PagingState(
                pages = listOf(page),
                anchorPosition = 3,
                config = PagingConfig(pageSize = 20),
                leadingPlaceholderCount = 0,
            )

        pagingSource = MoviesPagingSource { mockk() }

        val key = pagingSource.getRefreshKey(state)

        assertEquals(1, key)
    }

    @Test
    fun `getRefreshKey returns null when no anchorPosition`() {
        val state =
            PagingState<Int, NetworkMovieResponse>(
                pages = emptyList(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 20),
                leadingPlaceholderCount = 0,
            )

        pagingSource = MoviesPagingSource { mockk() }

        val key = pagingSource.getRefreshKey(state)

        assertEquals(null, key)
    }

    @Test
    fun `getRefreshKey returns null when unable to find page`() {
        val state =
            PagingState<Int, NetworkMovieResponse>(
                pages = emptyList(),
                anchorPosition = 10,
                config = PagingConfig(pageSize = 20),
                leadingPlaceholderCount = 0,
            )

        pagingSource = MoviesPagingSource { mockk() }

        val key = pagingSource.getRefreshKey(state)

        assertEquals(null, key)
    }
}
