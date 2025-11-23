package dev.esteban.movies.data.datasource.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMoviesResponse(
    val dates: NetworkDateRangeResponse? = null,
    val page: Int,
    val results: List<NetworkMovieResponse>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

@Serializable
data class NetworkDateRangeResponse(
    val maximum: String,
    val minimum: String
)

@Serializable
data class NetworkMovieResponse(
    val id: Int,
    val title: String,
    val overview: String,
    val video: Boolean,
    val adult: Boolean,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)
