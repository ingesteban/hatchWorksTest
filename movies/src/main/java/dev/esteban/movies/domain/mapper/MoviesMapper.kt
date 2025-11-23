package dev.esteban.movies.domain.mapper

import dev.esteban.movies.data.datasource.remote.model.NetworkMovieResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkMoviesResponse
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.network.Mapper
import dev.esteban.network.ResponseState
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MoviesMapper @Inject constructor() :
    Mapper<NetworkMoviesResponse, ResponseState<List<MovieModel>>> {
    override fun apply(input: NetworkMoviesResponse): ResponseState<List<MovieModel>> {
        return ResponseState.Success(input.results.map { movieResponse ->
            getMovieModel(movieResponse)
        })
    }

    private fun getMovieModel(movieResponse: NetworkMovieResponse) = MovieModel(
        id = movieResponse.id,
        title = movieResponse.title,
        originalTitle = movieResponse.originalTitle,
        overview = movieResponse.overview,
        video = movieResponse.video,
        adult = movieResponse.adult,
        posterPath = movieResponse.posterPath,
        backdropPath = movieResponse.backdropPath,
        genreIds = movieResponse.genreIds,
        originalLanguage = movieResponse.originalLanguage,
        popularity = movieResponse.popularity,
        releaseDate = movieResponse.releaseDate,
        voteAverage = movieResponse.voteAverage,
        voteCount = movieResponse.voteCount,
    )
}
