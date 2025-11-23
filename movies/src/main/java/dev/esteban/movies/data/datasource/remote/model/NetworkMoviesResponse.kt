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
