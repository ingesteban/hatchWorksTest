package dev.esteban.movies.data.repository

import dev.esteban.movies.data.datasource.remote.genres.GenresDataSource
import dev.esteban.movies.data.datasource.remote.model.NetworkGenresResponse
import dev.esteban.movies.domain.mapper.GenresMapper
import dev.esteban.movies.domain.model.GenreModel
import dev.esteban.network.ResponseState
import dev.esteban.network.error.ErrorType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class GenresRepositoryImplTest {
    private lateinit var repository: GenresRepositoryImpl
    private val dataSource: GenresDataSource = mockk()
    private val genresMapper: GenresMapper = mockk(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        repository =
            GenresRepositoryImpl(
                ioDispatcher = testDispatcher,
                genresMapper = genresMapper,
                dataSource = dataSource,
            )
    }

    @Test
    fun `genreList should emit Success when mapper returns success`() =
        runTest {
            val networkGenresResponse: NetworkGenresResponse = mockk()
            val response =
                ResponseState.Success(
                    listOf(GenreModel(id = 1, name = "Action")),
                )

            coEvery { dataSource.genreList() } returns networkGenresResponse
            coEvery { genresMapper.apply(networkGenresResponse) } returns response

            val flow = repository.genreList()

            assertEquals(response, flow.last())
        }

    @Test
    fun `genreList should emit Error when dataSource throws exception`() =
        runTest {
            coEvery { dataSource.genreList() } throws IOException("Network error")

            val flow = repository.genreList()

            val last = flow.last()

            assertTrue(last is ResponseState.Error)
            assertEquals(ErrorType.UNKNOWN, (last as ResponseState.Error).errorType)
        }
}
