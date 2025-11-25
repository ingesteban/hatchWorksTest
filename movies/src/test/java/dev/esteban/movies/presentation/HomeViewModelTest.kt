package dev.esteban.movies.presentation

import dev.esteban.movies.domain.model.GenreModel
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.movies.domain.repository.GenresRepository
import dev.esteban.movies.domain.repository.MoviesRepository
import dev.esteban.movies.presentation.viewmodel.HomeViewModel
import dev.esteban.network.ResponseState
import dev.esteban.network.error.ErrorBody
import dev.esteban.network.error.ErrorType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
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
class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel
    private val moviesRepository: MoviesRepository = mockk()
    private val genresRepository: GenresRepository = mockk()
    private val movieModel: MovieModel = mockk()
    private val genreModel: GenreModel = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private val mockMovies = ResponseState.Success(listOf(movieModel))
    private val mockGenres = ResponseState.Success(listOf(genreModel))
    private val mockError =
        ResponseState.Error(
            errorType = ErrorType.DEFAULT,
            errorBody = ErrorBody(false, 500, "Server Error"),
            httpErrorCode = 500,
        )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(moviesRepository, genresRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadMovies should update all movie lists and genres with success data`() =
        runTest {
            coEvery { moviesRepository.trending() } returns flowOf(ResponseState.Loading, mockMovies)
            coEvery { moviesRepository.popular() } returns flowOf(ResponseState.Loading, mockMovies)
            coEvery { moviesRepository.upcoming() } returns flowOf(ResponseState.Loading, mockMovies)
            coEvery { moviesRepository.nowPlaying() } returns flowOf(ResponseState.Loading, mockMovies)
            coEvery { genresRepository.genreList() } returns flowOf(ResponseState.Loading, mockGenres)

            viewModel.loadMovies()
            advanceUntilIdle()

            val state = viewModel.homeMoviesStateFlow.value

            assertEquals(mockMovies, state.trending)
            assertEquals(mockMovies, state.popular)
            assertEquals(mockMovies, state.upcoming)
            assertEquals(mockMovies, state.nowPlaying)
            assertEquals(mockGenres, state.genres)
            assertTrue(state.trending is ResponseState.Success)
        }

    @Test
    fun `loadMovies should update trending list with Error when its repository fails`() =
        runTest {
            coEvery { moviesRepository.trending() } returns flowOf(ResponseState.Loading, mockError)
            coEvery { moviesRepository.popular() } returns flowOf(ResponseState.Loading, mockMovies)
            coEvery { genresRepository.genreList() } returns flowOf(ResponseState.Loading, mockGenres)
            coEvery { moviesRepository.upcoming() } returns flowOf(ResponseState.Loading, mockMovies)
            coEvery { moviesRepository.nowPlaying() } returns flowOf(ResponseState.Loading, mockMovies)

            viewModel.loadMovies()
            advanceUntilIdle()

            val state = viewModel.homeMoviesStateFlow.value
            assertEquals(mockError, state.trending)
            assertEquals(mockMovies, state.popular)
            assertEquals(mockGenres, state.genres)
        }

    @Test
    fun `loadMovies should update genre list with Error when its repository fails`() =
        runTest {
            coEvery { moviesRepository.trending() } returns flowOf(ResponseState.Loading, mockMovies)
            coEvery { genresRepository.genreList() } returns flowOf(ResponseState.Loading, mockError)
            coEvery { moviesRepository.popular() } returns flowOf(ResponseState.Loading, mockMovies)
            coEvery { moviesRepository.upcoming() } returns flowOf(ResponseState.Loading, mockMovies)
            coEvery { moviesRepository.nowPlaying() } returns flowOf(ResponseState.Loading, mockMovies)

            viewModel.loadMovies()
            advanceUntilIdle()

            val state = viewModel.homeMoviesStateFlow.value
            assertEquals(mockError, state.genres)
            assertEquals(mockMovies, state.trending)
            assertEquals(mockMovies, state.popular)
        }
}
