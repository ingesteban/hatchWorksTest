package dev.esteban.movies.data.datasource.remote.movies.paging

import androidx.paging.PagingSource
import dev.esteban.movies.data.datasource.remote.model.NetworkMovieResponse
import dev.esteban.movies.data.datasource.remote.movies.MoviesApi
import dev.esteban.movies.util.MoviesEndpointType
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Factory to create instances of [MoviesPagingSource].
 * manage the services calls
 *
 * @property moviesApi retrofit interface passed to [MoviesPagingSource]
 **/
@Singleton
class MoviesPagingSourceFactory
    @Inject
    constructor(
        val moviesApi: MoviesApi,
    ) {
        fun create(
            type: MoviesEndpointType,
            genres: String? = null,
        ): PagingSource<Int, NetworkMovieResponse> =
            MoviesPagingSource { page ->
                when (type) {
                    MoviesEndpointType.TRENDING -> moviesApi.trending(page)
                    MoviesEndpointType.UPCOMING -> moviesApi.upcoming(page)
                    MoviesEndpointType.GENRE -> moviesApi.discover(page = page, genres = genres ?: "")
                    else -> moviesApi.nowPlaying(page)
                }
            }
    }
