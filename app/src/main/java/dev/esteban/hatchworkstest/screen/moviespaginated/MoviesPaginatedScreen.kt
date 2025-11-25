package dev.esteban.hatchworkstest.screen.moviespaginated

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import dev.esteban.hatchworkstest.designsystem.components.GenericMovieItem
import dev.esteban.hatchworkstest.designsystem.components.LoadingWheel
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.sm
import dev.esteban.movies.presentation.viewmodel.MoviesViewModel
import dev.esteban.movies.util.MoviesEndpointType

@Composable
fun MoviesPaginatedScreen(
    moviesViewModel: MoviesViewModel = hiltViewModel(),
    type: MoviesEndpointType,
    genres: String? = null,
    navigateToMovieDetail: (String, String) -> Unit,
) {
    val movies = moviesViewModel
        .movies(type, genres)
        .collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(sm),
        verticalArrangement = Arrangement.spacedBy(sm),
        horizontalArrangement = Arrangement.spacedBy(sm)
    ) {
        items(movies.itemCount) { index ->
            val movie = movies[index]
            if (movie != null) {
                GenericMovieItem(movie, navigateToMovieDetail)
            }
        }
        movies.apply {
            when {
                loadState.refresh is LoadState.Loading ->
                    item { LoadingWheel() }

                loadState.append is LoadState.Loading ->
                    item { LoadingMoreItem() }

                loadState.refresh is LoadState.Error ->
                    item { ErrorItem(retry = { movies.retry() }) }

                loadState.append is LoadState.Error ->
                    item { ErrorItem(retry = { movies.retry() }) }
            }
        }
    }
}

@Composable
fun LoadingMoreItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(strokeWidth = 2.dp)
    }
}

@Composable
fun ErrorItem(retry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Algo salió mal", color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = retry) {
            Text("Reintentar")
        }
    }
}
