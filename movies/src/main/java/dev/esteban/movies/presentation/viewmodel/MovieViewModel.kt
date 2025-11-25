package dev.esteban.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.esteban.movies.domain.model.MovieDetailModel
import dev.esteban.movies.domain.repository.MoviesRepository
import dev.esteban.network.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {
    private val movieDetailMutableStateFlow: MutableStateFlow<ResponseState<dev.esteban.movies.domain.model.MovieDetailModel>> =
        MutableStateFlow(ResponseState.Idle)
    val movieDetailStateFlow: StateFlow<ResponseState<MovieDetailModel>> =
        movieDetailMutableStateFlow.asStateFlow()

    fun movieDetail(movieId: String) {
        viewModelScope.launch {
            moviesRepository.movieDetail(movieId)
                .collect { response ->
                    when (response) {
                        is ResponseState.Idle -> {
                            // NO_OP
                        }

                        is ResponseState.Loading -> movieDetailMutableStateFlow.emit(
                            ResponseState.Loading
                        )

                        is ResponseState.Success -> movieDetailMutableStateFlow.emit(
                            response
                        )

                        is ResponseState.Error -> movieDetailMutableStateFlow.emit(
                            response
                        )
                    }
                }
        }
    }
}
