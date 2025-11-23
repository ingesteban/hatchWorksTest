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
import dev.esteban.movies.presentation.viewmodel.HomeViewModel

@Composable
fun MoviesScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    goToDetail: (String) -> Unit
) {
    val homeMoviesStateFlow by viewModel.homeMoviesStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadMovies()
    }

    Column(Modifier.fillMaxSize()) {

        homeMoviesStateFlow.nowPlaying?.let {
            NowPlaying(it)
        }

        Text("Movie Screen")

        Button(onClick = { goToDetail("1") }) {
            Text("Navigate to detail")
        }
    }
}

@Composable
fun NowPlaying(
    nowPlayingMovies: List<MovieModel>,
) {
    LazyRow {
        items(nowPlayingMovies) { movieItem ->
            Card {
                //TODO : add element to render view
                Text("Item " + movieItem.title)
            }
        }
    }
}
