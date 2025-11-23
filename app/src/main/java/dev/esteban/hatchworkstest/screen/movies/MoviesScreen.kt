package dev.esteban.hatchworkstest.screen.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.network.ResponseState

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    goToDetail: (String) -> Unit
) {
    val nowPlayingMoviesState by viewModel.nowPlayingMoviesStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getNowPlayingMovies()
    }


    Column(Modifier.fillMaxSize()) {
        NowPlaying(nowPlayingMoviesState)

        Text("Movie Screen")

        Button(onClick = { goToDetail("1") }) {
            Text("Navigate to detail")
        }
    }
}

@Composable
fun NowPlaying(
    nowPlayingMoviesState: ResponseState<List<MovieModel>>,
) {
    when (nowPlayingMoviesState) {
        is ResponseState.Loading -> {
            Text("Loading")
        }

        is ResponseState.Success -> {
            val response = nowPlayingMoviesState.response
            LazyRow {
                items(response) { movieItem ->
                    Card {
                        //TODO : add element to render view
                        Text("Item " + movieItem.title)
                    }
                }
            }
        }

        is ResponseState.Error -> {
            Text("Error")
            //if (ordersState.errorType == ErrorType.NO_DATA) {
//                LatestOrdersEmpty(navigateToHome = navigateToHome)
//            }
        }

        else -> Unit
    }
}
