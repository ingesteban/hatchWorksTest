package dev.esteban.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.movies.domain.repository.MoviesRepository
import dev.esteban.movies.util.MoviesEndpointType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(val moviesRepository: MoviesRepository) : ViewModel() {
    fun movies(
        type: MoviesEndpointType,
        genres: String? = null
    ): Flow<PagingData<MovieModel>> {
        return moviesRepository.getMoviesPagingByType(type, genres)
            .cachedIn(viewModelScope)
    }
}
