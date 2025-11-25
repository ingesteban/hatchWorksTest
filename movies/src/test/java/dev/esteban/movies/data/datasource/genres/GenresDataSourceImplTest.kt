package dev.esteban.movies.data.datasource.genres

import dev.esteban.movies.data.datasource.remote.genres.GenresApi
import dev.esteban.movies.data.datasource.remote.genres.GenresDataSourceImpl
import dev.esteban.movies.data.datasource.remote.model.NetworkGenresResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GenresDataSourceImplTest {

    private lateinit var dataSource: GenresDataSourceImpl
    private val testDispatcher = StandardTestDispatcher()

    private val genresApi: GenresApi = mockk()

    @Before
    fun setup() {
        dataSource = GenresDataSourceImpl(genresApi)
    }

    @Test
    fun `genreList should call api`() = runTest(testDispatcher) {
        val expectedResponse: NetworkGenresResponse = mockk()
        coEvery { genresApi.genreList() } returns expectedResponse
        val result = dataSource.genreList()

        assertEquals(expectedResponse, result)
        coVerify(exactly = 1) { genresApi.genreList() }
    }

    @Test
    fun `genreList should call api with error`() = runTest(testDispatcher) {
        val exception = IOException("Get genres failed")

        coEvery { genresApi.genreList() } throws exception
        try {
            dataSource.genreList()
            assert(false) { "Expected exception to be thrown" }
        } catch (e: Exception) {
            assertEquals("Get genres failed", e.message)
        }

        coVerify(exactly = 1) { genresApi.genreList() }
    }
}
