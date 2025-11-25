package dev.esteban.movies.presentation

import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.movies.domain.repository.MoviesRepository
import dev.esteban.movies.presentation.viewmodel.SearchViewModel
import dev.esteban.network.ResponseState
import dev.esteban.network.error.ErrorBody
import dev.esteban.network.error.ErrorType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel
    private val moviesRepository: MoviesRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel(moviesRepository)
    }

    private fun mockMovies() =
        listOf(
            MovieModel(
                id = 1,
                title = "Batman Begins",
                originalTitle = "Batman Begins",
                overview = "Origin story",
                video = false,
                adult = false,
                posterPath = "/poster1.jpg",
                backdropPath = "/back1.jpg",
                genreIds = listOf(18, 28),
                originalLanguage = "en",
                popularity = 80.0,
                releaseDate = "2005-06-15",
                voteAverage = 8.0,
                voteCount = 15000,
            ),
        )

    @Test
    fun `when query is blank, state must return Idle`() =
        runTest {
            val results = mutableListOf<ResponseState<List<MovieModel>>>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.searchedMoviesStateFlow.toList(results)
            }

            viewModel.searchMovies("")
            advanceUntilIdle()

            assertEquals(ResponseState.Idle, results.last())
        }

    @Test
    fun `searchMovies emits Loading then Success`() =
        runTest {
            val expected = mockMovies()
            coEvery { moviesRepository.search("batman") } returns
                flow {
                    emit(ResponseState.Loading)
                    emit(ResponseState.Success(expected))
                }
            val collected = mutableListOf<ResponseState<List<MovieModel>>>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.searchedMoviesStateFlow.toList(collected)
            }

            viewModel.searchMovies("batman")
            advanceUntilIdle()

            assertTrue(collected.contains(ResponseState.Loading))
            assertEquals(ResponseState.Success(expected), collected.last())
        }

    @Test
    fun `searchMovies emits Loading then Error`() =
        runTest {
            val errorState =
                ResponseState.Error(
                    errorType = ErrorType.DEFAULT,
                    errorBody =
                        ErrorBody(
                            success = false,
                            statusCode = 20,
                            statusMessage = "Search failed",
                        ),
                    httpErrorCode = 500,
                )
            coEvery { moviesRepository.search("fail") } returns
                flow {
                    emit(ResponseState.Loading)
                    emit(errorState)
                }
            val collected = mutableListOf<ResponseState<List<MovieModel>>>()

            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.searchedMoviesStateFlow.toList(collected)
            }

            viewModel.searchMovies("fail")
            advanceUntilIdle()

            assertTrue(collected.contains(ResponseState.Loading))
            assertEquals(errorState, collected.last())
        }

    @Test
    fun `searchMovies ignores Idle emissions from repository`() =
        runTest {
            coEvery { moviesRepository.search("idle") } returns flow { emit(ResponseState.Idle) }
            val collected = mutableListOf<ResponseState<List<MovieModel>>>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.searchedMoviesStateFlow.toList(collected)
            }

            viewModel.searchMovies("idle")
            advanceUntilIdle()

            assertEquals(ResponseState.Idle, collected.last())
        }

    @After
    fun tearDown() {
        kotlinx.coroutines.Dispatchers.resetMain()
    }
}
