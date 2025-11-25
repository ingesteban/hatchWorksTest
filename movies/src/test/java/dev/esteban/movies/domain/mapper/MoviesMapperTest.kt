package dev.esteban.movies.domain.mapper

import dev.esteban.movies.ConstantsUtils.IMAGE_URL_BACKDROP
import dev.esteban.movies.ConstantsUtils.IMAGE_URL_POSTER
import dev.esteban.movies.data.datasource.remote.model.NetworkMovieResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkMoviesResponse
import dev.esteban.network.ResponseState
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MoviesMapperTest {

    private lateinit var mapper: MoviesMapper

    @Before
    fun setup() {
        mapper = MoviesMapper()
    }

    @Test
    fun `convert valid movies correctly`() {
        val response = NetworkMoviesResponse(
            page = 1,
            results = listOf(
                NetworkMovieResponse(
                    id = 1,
                    title = "Movie 1",
                    originalTitle = "Original 1",
                    overview = "Overview 1",
                    video = false,
                    adult = false,
                    posterPath = "/poster1.jpg",
                    backdropPath = "/backdrop1.jpg",
                    genreIds = listOf(10, 11),
                    originalLanguage = "en",
                    popularity = 8.5,
                    releaseDate = "2024-01-01",
                    voteAverage = 7.8,
                    voteCount = 100
                ),
                NetworkMovieResponse(
                    id = 2,
                    title = "Movie 2",
                    originalTitle = "Original 2",
                    overview = "Overview 2",
                    video = true,
                    adult = false,
                    posterPath = "/poster2.jpg",
                    backdropPath = "/backdrop2.jpg",
                    genreIds = listOf(20, 21),
                    originalLanguage = "es",
                    popularity = 5.5,
                    releaseDate = "2024-02-02",
                    voteAverage = 8.0,
                    voteCount = 200
                )
            ),
            totalPages = 1,
            totalResults = 2
        )

        val result = mapper.apply(response)

        assertTrue(result is ResponseState.Success)
        val success = result as ResponseState.Success
        val movies = success.response

        assertEquals(2, movies.size)

        val first = movies[0]
        assertEquals(1, first.id)
        assertEquals("Movie 1", first.title)
        assertEquals("$IMAGE_URL_POSTER/poster1.jpg", first.posterPath)
        assertEquals("$IMAGE_URL_BACKDROP/backdrop1.jpg", first.backdropPath)
        assertEquals(listOf(10, 11), first.genreIds)

        val second = movies[1]
        assertEquals(2, second.id)
        assertEquals("Movie 2", second.title)
        assertEquals("$IMAGE_URL_POSTER/poster2.jpg", second.posterPath)
        assertEquals("$IMAGE_URL_BACKDROP/backdrop2.jpg", second.backdropPath)
        assertEquals(listOf(20, 21), second.genreIds)
    }

    @Test
    fun `convert empty movies list correctly`() {
        val response = NetworkMoviesResponse(
            page = 1,
            results = emptyList(),
            totalPages = 1,
            totalResults = 0
        )

        val result = mapper.apply(response)
        assertTrue(result is ResponseState.Success)
        val success = result as ResponseState.Success
        assertTrue(success.response.isEmpty())
    }

    @Test
    fun `convert minimal movie data`() {
        val response = NetworkMoviesResponse(
            page = 1,
            results = listOf(
                NetworkMovieResponse(
                    id = 0,
                    title = "",
                    originalTitle = "",
                    overview = "",
                    video = false,
                    adult = false,
                    posterPath = "",
                    backdropPath = "",
                    genreIds = emptyList(),
                    originalLanguage = "",
                    popularity = 0.0,
                    releaseDate = "",
                    voteAverage = 0.0,
                    voteCount = 0
                )
            ),
            totalPages = 1,
            totalResults = 1
        )

        val result = mapper.apply(response)
        val movie = (result as ResponseState.Success).response.first()

        assertEquals(0, movie.id)
        assertEquals("", movie.title)
        assertEquals(IMAGE_URL_POSTER, movie.posterPath)
        assertEquals(IMAGE_URL_BACKDROP, movie.backdropPath)
        assertTrue(movie.genreIds.isEmpty())
    }

    @Test
    fun `convert multiple movies consistently`() {
        val list = (1..5).map {
            NetworkMovieResponse(
                id = it,
                title = "Movie $it",
                originalTitle = "Original $it",
                overview = "Overview $it",
                video = false,
                adult = false,
                posterPath = "/poster$it.jpg",
                backdropPath = "/backdrop$it.jpg",
                genreIds = listOf(it),
                originalLanguage = "en",
                popularity = it.toDouble(),
                releaseDate = "2024-0$it-01",
                voteAverage = it.toDouble(),
                voteCount = it * 10
            )
        }

        val response = NetworkMoviesResponse(
            page = 1,
            results = list,
            totalPages = 1,
            totalResults = 5
        )

        val result = mapper.apply(response)
        val movies = (result as ResponseState.Success).response

        assertEquals(5, movies.size)
        assertEquals(5, movies.last().id)
        assertEquals("Movie 5", movies.last().title)
        assertEquals("$IMAGE_URL_POSTER/poster5.jpg", movies.last().posterPath)
    }

    @Test
    fun `verify mapped values integrity`() {
        val response = NetworkMoviesResponse(
            page = 1,
            results = listOf(
                NetworkMovieResponse(
                    id = 100,
                    title = "Epic Movie",
                    originalTitle = "Epic Original",
                    overview = "Some overview text",
                    video = false,
                    adult = false,
                    posterPath = "/epic.jpg",
                    backdropPath = "/epic_bg.jpg",
                    genreIds = listOf(99),
                    originalLanguage = "en",
                    popularity = 999.0,
                    releaseDate = "2024-10-10",
                    voteAverage = 10.0,
                    voteCount = 9000
                )
            ),
            totalPages = 1,
            totalResults = 1
        )

        val result = mapper.apply(response)
        val movie = (result as ResponseState.Success).response.first()

        assertEquals(100, movie.id)
        assertEquals("Epic Movie", movie.title)
        assertTrue(movie.posterPath?.contains("/epic.jpg") == true)
        assertTrue(movie.backdropPath?.contains("/epic_bg.jpg") == true)
        assertTrue(movie.genreIds.isNotEmpty())
        assertTrue(movie.title.isNotBlank())
    }
}
