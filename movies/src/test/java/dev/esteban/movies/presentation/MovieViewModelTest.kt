package dev.esteban.movies.presentation

import dev.esteban.movies.domain.model.MovieDetailModel
import dev.esteban.movies.domain.repository.MoviesRepository
import dev.esteban.movies.presentation.viewmodel.MovieViewModel
import dev.esteban.network.ResponseState
import dev.esteban.network.error.ErrorBody
import dev.esteban.network.error.ErrorType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel
    private val moviesRepository: MoviesRepository = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieViewModel(moviesRepository)
    }

    private fun mockMovieDetail() =
        MovieDetailModel(
            adult = false,
            backdropPath = "/backdrop.jpg",
            belongsToCollection = null,
            budget = 185000000,
            genres = emptyList(),
            homepage = null,
            id = 10,
            imdbId = "tt0468569",
            originCountry = listOf("US"),
            originalLanguage = "en",
            originalTitle = "Original",
            overview = "Nice movie",
            popularity = 1000.0,
            posterPath = "/poster.jpg",
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            releaseDate = "2020-01-01",
            revenue = 100_000_000,
            runtime = 120,
            spokenLanguages = emptyList(),
            status = "Released",
            tagline = "Epic tagline",
            title = "Movie Title",
            video = false,
            voteAverage = 8.5,
            voteCount = 10000,
        )

    @Test
    fun `movieDetail should emit Loading and then Success when repo returns Success`() =
        runTest {
            val expectedState = ResponseState.Success(mockMovieDetail())
            coEvery {
                moviesRepository.movieDetail("10")
            } returns flowOf(ResponseState.Loading, expectedState)
            val emissions = mutableListOf<ResponseState<MovieDetailModel>>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.movieDetailStateFlow.toList(emissions)
            }

            viewModel.movieDetail("10")
            advanceUntilIdle()

            assertEquals(ResponseState.Idle, emissions.first())
            assertTrue(emissions.contains(ResponseState.Loading))
            assertEquals(expectedState, emissions.last())
        }

    @Test
    fun `movieDetail should emit Loading and then Error when repo returns Error`() =
        runTest {
            val errorState =
                ResponseState.Error(
                    errorType = ErrorType.DEFAULT,
                    errorBody =
                        ErrorBody(
                            success = false,
                            statusCode = 404,
                            statusMessage = "Not Found",
                        ),
                    httpErrorCode = 404,
                )
            coEvery { moviesRepository.movieDetail("10") } returns
                flowOf(
                    ResponseState.Loading,
                    errorState,
                )
            val emissions = mutableListOf<ResponseState<MovieDetailModel>>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.movieDetailStateFlow.toList(emissions)
            }

            viewModel.movieDetail("10")
            advanceUntilIdle()

            assertEquals(ResponseState.Idle, emissions.first())
            assertTrue(emissions.contains(ResponseState.Loading))
            assertEquals(errorState, emissions.last())
        }

    @Test
    fun `movieDetail should stay Idle when repository returns only Idle`() =
        runTest {
            coEvery { moviesRepository.movieDetail("10") } returns flowOf(ResponseState.Idle)
            val emissions = mutableListOf<ResponseState<MovieDetailModel>>()

            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.movieDetailStateFlow.toList(emissions)
            }

            viewModel.movieDetail("10")
            advanceUntilIdle()

            assertEquals(listOf(ResponseState.Idle), emissions.distinct())
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
