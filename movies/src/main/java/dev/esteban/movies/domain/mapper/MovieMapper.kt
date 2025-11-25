package dev.esteban.movies.domain.mapper

import androidx.paging.PagingData
import androidx.paging.map
import dev.esteban.movies.ConstantsUtils.IMAGE_URL
import dev.esteban.movies.data.datasource.remote.model.NetworkMovieResponse
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.network.Mapper
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieMapper @Inject constructor() :
    Mapper<PagingData<NetworkMovieResponse>, PagingData<MovieModel>> {
    override fun apply(input: PagingData<NetworkMovieResponse>): PagingData<MovieModel> {
        return input.map { movieResponse ->
            getMovieModel(movieResponse)
        }
    }

    fun getMovieModel(movieResponse: NetworkMovieResponse) = MovieModel(
        id = movieResponse.id,
        title = movieResponse.title,
        originalTitle = movieResponse.originalTitle,
        overview = movieResponse.overview,
        video = movieResponse.video,
        adult = movieResponse.adult,
        posterPath = IMAGE_URL + movieResponse.posterPath,
        backdropPath = IMAGE_URL + movieResponse.backdropPath,
        genreIds = movieResponse.genreIds,
        originalLanguage = movieResponse.originalLanguage,
        popularity = movieResponse.popularity,
        releaseDate = movieResponse.releaseDate,
        voteAverage = movieResponse.voteAverage,
        voteCount = movieResponse.voteCount,
    )
}
