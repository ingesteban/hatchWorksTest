package dev.esteban.movies.data.datasource.remote.genres

import dev.esteban.movies.ConstantsURL.GENRE_LIST
import dev.esteban.movies.data.datasource.remote.model.NetworkGenresResponse
import retrofit2.http.GET

interface GenresApi {
    @GET(GENRE_LIST)
    suspend fun genreList(): NetworkGenresResponse
}