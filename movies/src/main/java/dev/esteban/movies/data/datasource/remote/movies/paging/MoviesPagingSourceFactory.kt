package dev.esteban.movies.data.datasource.remote.movies.paging

import androidx.paging.PagingSource
import dev.esteban.movies.data.datasource.remote.model.NetworkMovieResponse
import dev.esteban.movies.data.datasource.remote.movies.MoviesApi
import dev.esteban.movies.util.MoviesEndpointType
import dev.esteban.network.RetrofitBuilder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesPagingSourceFactory @Inject constructor(retrofitBuilder: RetrofitBuilder) {
    private val api = retrofitBuilder.create<MoviesApi>()
    fun create(
        type: MoviesEndpointType,
        genres: String? = null
    ): PagingSource<Int, NetworkMovieResponse> {
        return MoviesPagingSource { page ->
            when (type) {
                MoviesEndpointType.TRENDING -> api.trending(page)
                MoviesEndpointType.UPCOMING -> api.upcoming(page)
                MoviesEndpointType.GENRE -> api.discover(page = page, genres = genres ?: "")
                else -> api.nowPlaying(page)
            }
        }
    }
}
