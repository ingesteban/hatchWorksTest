package dev.esteban.movies.domain.mapper

import dev.esteban.movies.data.datasource.remote.model.NetworkGenreResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkGenresResponse
import dev.esteban.network.ResponseState
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GenresMapperTest {
    private lateinit var mapper: GenresMapper

    @Before
    fun setup() {
        mapper = GenresMapper()
    }

    @Test
    fun `convert valid genres correctly`() {
        val response =
            NetworkGenresResponse(
                genres =
                    listOf(
                        NetworkGenreResponse(id = 1, name = "Action"),
                        NetworkGenreResponse(id = 2, name = "Drama"),
                    ),
            )

        val result = mapper.apply(response)

        assertTrue(result is ResponseState.Success)
        val success = result as ResponseState.Success
        val genres = success.response
        assertEquals(2, genres.size)
        assertEquals(1, genres[0].id)
        assertEquals("Action", genres[0].name)
        assertEquals(2, genres[1].id)
        assertEquals("Drama", genres[1].name)
    }

    @Test
    fun `convert empty genres list without errors`() {
        val response = NetworkGenresResponse(genres = emptyList())

        val result = mapper.apply(response)

        assertTrue(result is ResponseState.Success)
        val success = result as ResponseState.Success

        assertTrue(success.response.isEmpty())
    }

    @Test
    fun `convert minimal genre data`() {
        val response =
            NetworkGenresResponse(
                genres =
                    listOf(
                        NetworkGenreResponse(id = 0, name = ""),
                    ),
            )
        val result = mapper.apply(response)
        val success = result as ResponseState.Success
        val genre = success.response.first()

        assertEquals(0, genre.id)
        assertEquals("", genre.name)
    }

    @Test
    fun `handle multiple genres consistently`() {
        val response =
            NetworkGenresResponse(
                genres =
                    (1..5).map {
                        NetworkGenreResponse(id = it, name = "Genre $it")
                    },
            )

        val result = mapper.apply(response)
        val success = result as ResponseState.Success
        val genres = success.response

        assertEquals(5, genres.size)
        assertEquals("Genre 5", genres.last().name)
        assertEquals(5, genres.last().id)
    }

    @Test
    fun `verify mapped values integrity`() {
        val response =
            NetworkGenresResponse(
                genres =
                    listOf(
                        NetworkGenreResponse(id = 10, name = "Fantasy"),
                    ),
            )

        val result = mapper.apply(response)
        val success = result as ResponseState.Success
        val genre = success.response.first()

        assertEquals(10, genre.id)
        assertEquals("Fantasy", genre.name)
        assertTrue(genre.name.isNotBlank())
    }
}
