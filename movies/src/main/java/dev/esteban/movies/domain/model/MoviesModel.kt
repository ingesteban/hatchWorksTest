package dev.esteban.movies.domain.model

data class MovieModel(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val video: Boolean,
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int
)
