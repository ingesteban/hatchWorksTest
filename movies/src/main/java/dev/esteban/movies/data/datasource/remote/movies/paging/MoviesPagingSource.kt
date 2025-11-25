package dev.esteban.movies.data.datasource.remote.movies.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.esteban.movies.data.datasource.remote.model.NetworkMovieResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkMoviesResponse

class MoviesPagingSource(
    private val fetchMovies: suspend (page: Int) -> NetworkMoviesResponse,
) : PagingSource<Int, NetworkMovieResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NetworkMovieResponse> {
        val page = params.key ?: 1

        return try {
            val response = fetchMovies(page)

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.totalPages) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NetworkMovieResponse>): Int? {
        val anchor = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchor)
        return page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
    }
}
