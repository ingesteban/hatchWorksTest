package dev.esteban.movies.presentation

import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.movies.domain.repository.MoviesRepository
import dev.esteban.movies.presentation.viewmodel.MoviesViewModel
import dev.esteban.movies.util.MoviesEndpointType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel
    private val repository: MoviesRepository = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        viewModel = MoviesViewModel(repository)
    }

    private fun mockMovie(id: Int, title: String = "Movie $id") = MovieModel(
        id = id,
        title = title,
        originalTitle = title,
        overview = "overview",
        video = false,
        adult = false,
        backdropPath = null,
        genreIds = listOf(1, 2),
        originalLanguage = "en",
        popularity = 0.0,
        posterPath = null,
        releaseDate = "2020-01-01",
        voteAverage = 7.5,
        voteCount = 100
    )

    private val diffCallback = object : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
            oldItem == newItem
    }

    private val updateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(from: Int, to: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    private suspend fun PagingData<MovieModel>.toList(): List<MovieModel> {
        val differ = androidx.paging.AsyncPagingDataDiffer(
            diffCallback = diffCallback,
            updateCallback = updateCallback,
            mainDispatcher = testDispatcher,
            workerDispatcher = testDispatcher
        )

        differ.submitData(this)

        return differ.snapshot().items
    }

    @Test
    fun `movies returns paging data from repository`() = runTest(testDispatcher) {
        val fakeMovies = listOf(
            mockMovie(1, "Batman"),
            mockMovie(2, "Superman")
        )

        val pagingData = PagingData.from(fakeMovies)

        coEvery {
            repository.getMoviesPagingByType(
                MoviesEndpointType.NOW_PLAYING,
                null
            )
        } returns flowOf(pagingData)

        val resultFlow: Flow<PagingData<MovieModel>> =
            viewModel.movies(MoviesEndpointType.NOW_PLAYING)

        val collected = resultFlow.first().toList()

        assertEquals(2, collected.size)
        assertEquals("Batman", collected[0].title)
        assertEquals("Superman", collected[1].title)
    }

    @Test
    fun `movies sends genres parameter to repository`() = runTest(testDispatcher) {
        val pagingData = PagingData.from(
            listOf(mockMovie(10, "Action Movie"))
        )
        coEvery {
            repository.getMoviesPagingByType(MoviesEndpointType.GENRE, "28")
        } returns flowOf(pagingData)
        val resultFlow = viewModel.movies(MoviesEndpointType.GENRE, "28")
        val collected = resultFlow.first().toList()

        assertEquals(1, collected.size)
        assertEquals("Action Movie", collected[0].title)
    }

    @Test
    fun `movies returns empty list when repository emits empty paging`() = runTest(testDispatcher) {
        val pagingData = PagingData.from(emptyList<MovieModel>())

        coEvery {
            repository.getMoviesPagingByType(MoviesEndpointType.UPCOMING, null)
        } returns flowOf(pagingData)

        val collected = viewModel.movies(MoviesEndpointType.UPCOMING)
            .first()
            .toList()

        assertEquals(0, collected.size)
    }
}
