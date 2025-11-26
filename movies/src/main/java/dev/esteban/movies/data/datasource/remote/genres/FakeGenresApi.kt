package dev.esteban.movies.data.datasource.remote.genres

import dev.esteban.movies.data.datasource.remote.model.NetworkGenreResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkGenresResponse

/**
 * FakeGenresApi
 * Class should be used to get fake data on instrumented tests
**/
class FakeGenresApi : GenresApi {
    override suspend fun genreList(): NetworkGenresResponse {
        val tmdbGenres = listOf(
            NetworkGenreResponse(28, "Action"),
            NetworkGenreResponse(12, "Adventure"),
            NetworkGenreResponse(16, "Animation"),
            NetworkGenreResponse(35, "Comedy"),
            NetworkGenreResponse(80, "Crime"),
            NetworkGenreResponse(99, "Documentary"),
            NetworkGenreResponse(18, "Drama"),
            NetworkGenreResponse(10751, "Family"),
            NetworkGenreResponse(14, "Fantasy"),
            NetworkGenreResponse(36, "History"),
            NetworkGenreResponse(27, "Horror"),
            NetworkGenreResponse(10402, "Music"),
            NetworkGenreResponse(9648, "Mystery"),
            NetworkGenreResponse(10749, "Romance"),
            NetworkGenreResponse(878, "Science Fiction"),
            NetworkGenreResponse(10770, "TV Movie"),
            NetworkGenreResponse(53, "Thriller"),
            NetworkGenreResponse(10752, "War"),
            NetworkGenreResponse(37, "Western")
        )
        return NetworkGenresResponse(genres = tmdbGenres)
    }
}
