package dev.esteban.movies.domain.mapper

import dev.esteban.movies.ConstantsUtils.IMAGE_URL_BACKDROP
import dev.esteban.movies.ConstantsUtils.IMAGE_URL_POSTER
import dev.esteban.movies.data.datasource.remote.model.NetworkCollectionInfoResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkGenreResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkMovieDetailResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkProductionCompanyResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkProductionCountryResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkSpokenLanguageResponse
import dev.esteban.network.ResponseState
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MovieDetailMapperTest {

    private lateinit var mapper: MovieDetailMapper

    @Before
    fun setup() {
        mapper = MovieDetailMapper()
    }

    @Test
    fun `map full movie detail correctly`() {
        val input = NetworkMovieDetailResponse(
            adult = false,
            backdropPath = "/back.jpg",
            belongsToCollection = NetworkCollectionInfoResponse(
                id = 10,
                name = "The Collection",
                posterPath = "/collPoster.jpg",
                backdropPath = "/collBackdrop.jpg"
            ),
            budget = 150_000_000,
            genres = listOf(NetworkGenreResponse(id = 1, name = "Action")),
            homepage = "https://example.com",
            id = 100,
            imdbId = "tt1234567",
            originCountry = listOf("US"),
            originalLanguage = "en",
            originalTitle = "Original Movie",
            overview = "A great movie",
            popularity = 200.5,
            posterPath = "/poster.jpg",
            productionCompanies = listOf(
                NetworkProductionCompanyResponse(
                    id = 50,
                    logoPath = "/compLogo.png",
                    name = "Marvel",
                    originCountry = "US"
                )
            ),
            productionCountries = listOf(
                NetworkProductionCountryResponse(iso = "US", name = "United States")
            ),
            releaseDate = "2024-01-01",
            revenue = 500_000_000,
            runtime = 120,
            spokenLanguages = listOf(
                NetworkSpokenLanguageResponse(
                    englishName = "English",
                    iso = "en",
                    name = "English"
                )
            ),
            status = "Released",
            tagline = "An awesome tagline",
            title = "Movie Title",
            video = false,
            voteAverage = 8.5,
            voteCount = 15000
        )

        val result = mapper.apply(input)
        assertTrue(result is ResponseState.Success)
        val detail = (result as ResponseState.Success).response

        assertFalse(detail.adult)
        assertEquals(100, detail.id)
        assertEquals("Movie Title", detail.title)
        assertEquals("A great movie", detail.overview)
        assertEquals(120, detail.runtime)

        assertTrue(
            detail.posterPath != null && input.posterPath != null && detail.posterPath.contains(
                input.posterPath
            )
        )
        assertTrue(
            detail.backdropPath != null && input.backdropPath != null && detail.backdropPath.contains(
                input.backdropPath
            )
        )

        assertEquals(1, detail.genres.size)
        assertEquals("Action", detail.genres.first().name)
        assertNotNull(detail.belongsToCollection)
        val collection = detail.belongsToCollection!!
        assertEquals(10, collection.id)
        assertEquals("$IMAGE_URL_BACKDROP/collBackdrop.jpg", collection.backdropPath)
        assertEquals(1, detail.productionCompanies.size)
        assertEquals("Marvel", detail.productionCompanies.first().name)
        assertEquals(1, detail.productionCountries.size)
        assertEquals("United States", detail.productionCountries.first().name)
        assertEquals("English", detail.spokenLanguages.first().englishName)
    }

    @Test
    fun `map minimal movie detail`() {
        val input = NetworkMovieDetailResponse(
            adult = true,
            backdropPath = "",
            belongsToCollection = null,
            budget = 0,
            genres = emptyList(),
            homepage = null,
            id = 1,
            imdbId = null,
            originCountry = emptyList(),
            originalLanguage = "",
            originalTitle = "",
            overview = "",
            popularity = 0.0,
            posterPath = "",
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            releaseDate = "",
            revenue = 0,
            runtime = 0,
            spokenLanguages = emptyList(),
            status = "",
            tagline = "",
            title = "",
            video = false,
            voteAverage = 0.0,
            voteCount = 0
        )

        val result = mapper.apply(input)
        val detail = (result as ResponseState.Success).response

        assertTrue(detail.adult)
        assertEquals(1, detail.id)
        assertNull(detail.belongsToCollection)
        assertTrue(detail.genres.isEmpty())
        assertTrue(detail.productionCompanies.isEmpty())
        assertTrue(detail.productionCountries.isEmpty())
        assertTrue(detail.spokenLanguages.isEmpty())
    }

    @Test
    fun `map collection info correctly`() {
        val input = NetworkCollectionInfoResponse(
            id = 99,
            name = "Saga",
            posterPath = "/sagaPoster.jpg",
            backdropPath = "/sagaBackdrop.jpg"
        )

        val result = mapper.mapDetailToDomain(
            NetworkMovieDetailResponse(
                adult = false,
                backdropPath = "",
                belongsToCollection = input,
                budget = 0,
                genres = emptyList(),
                homepage = null,
                id = 1,
                imdbId = null,
                originCountry = emptyList(),
                originalLanguage = "",
                originalTitle = "",
                overview = "",
                popularity = 0.0,
                posterPath = "",
                productionCompanies = emptyList(),
                productionCountries = emptyList(),
                releaseDate = "",
                revenue = 0,
                runtime = 0,
                spokenLanguages = emptyList(),
                status = "",
                tagline = "",
                title = "",
                video = false,
                voteAverage = 0.0,
                voteCount = 0
            )
        )

        val collection = result.belongsToCollection!!
        assertEquals(99, collection.id)
        assertEquals(IMAGE_URL_POSTER + input.posterPath, collection.posterPath)
        assertEquals(IMAGE_URL_BACKDROP + input.backdropPath, collection.backdropPath)
    }

    @Test
    fun `map production company correctly`() {
        val company = NetworkProductionCompanyResponse(
            id = 1,
            logoPath = "/logo.png",
            name = "Pixar",
            originCountry = "US"
        )

        val result = mapper.mapDetailToDomain(
            NetworkMovieDetailResponse(
                adult = false,
                backdropPath = "",
                belongsToCollection = null,
                budget = 0,
                genres = emptyList(),
                homepage = null,
                id = 1,
                imdbId = null,
                originCountry = emptyList(),
                originalLanguage = "",
                originalTitle = "",
                overview = "",
                popularity = 0.0,
                posterPath = "",
                productionCompanies = listOf(company),
                productionCountries = emptyList(),
                releaseDate = "",
                revenue = 0,
                runtime = 0,
                spokenLanguages = emptyList(),
                status = "",
                tagline = "",
                title = "",
                video = false,
                voteAverage = 0.0,
                voteCount = 0
            )
        )

        val mapped = result.productionCompanies.first()
        assertEquals("Pixar", mapped.name)
        assertEquals(IMAGE_URL_POSTER + company.logoPath, mapped.logoPath)
    }

    @Test
    fun `map spoken language correctly`() {
        val lang = NetworkSpokenLanguageResponse(
            englishName = "Spanish",
            iso = "es",
            name = "Español"
        )

        val result = mapper.mapDetailToDomain(
            NetworkMovieDetailResponse(
                adult = false,
                backdropPath = "",
                belongsToCollection = null,
                budget = 0,
                genres = emptyList(),
                homepage = null,
                id = 1,
                imdbId = null,
                originCountry = emptyList(),
                originalLanguage = "",
                originalTitle = "",
                overview = "",
                popularity = 0.0,
                posterPath = "",
                productionCompanies = emptyList(),
                productionCountries = emptyList(),
                releaseDate = "",
                revenue = 0,
                runtime = 0,
                spokenLanguages = listOf(lang),
                status = "",
                tagline = "",
                title = "",
                video = false,
                voteAverage = 0.0,
                voteCount = 0
            )
        )

        val mapped = result.spokenLanguages.first()
        assertEquals("Spanish", mapped.englishName)
        assertEquals("es", mapped.iso)
        assertEquals("Español", mapped.name)
    }
}
