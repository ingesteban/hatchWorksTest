package dev.esteban.movies.data.datasource.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkGenresResponse(
    val genres: List<NetworkGenreResponse>
)

@Serializable
data class NetworkGenreResponse(
    val id: Int,
    val name: String
)
