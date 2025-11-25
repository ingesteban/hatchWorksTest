package dev.esteban.hatchworkstest.screen.moviessearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.esteban.hatchworkstest.R
import dev.esteban.hatchworkstest.designsystem.components.GenericMovieItem
import dev.esteban.hatchworkstest.designsystem.components.HatchTertiaryButton
import dev.esteban.hatchworkstest.designsystem.components.LoadingWheel
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.lg
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.md
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.sm
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xl
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xs
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxl
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxxxl
import dev.esteban.hatchworkstest.designsystem.theme.HatchWorksTestTheme
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.movies.presentation.viewmodel.SearchViewModel
import dev.esteban.network.ResponseState
import kotlinx.coroutines.delay

@Composable
fun MoviesSearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    navigateToMovieDetail: (String, String) -> Unit,
    onClose: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var query by remember { mutableStateOf("") }
    val colors = HatchWorksTestTheme.colors
    val searchedMoviesStateFlow by searchViewModel.searchedMoviesStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(query) {
        if (query.length >= 2) {
            delay(350)
            searchViewModel.searchMovies(query)
        }
    }

    Column {
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(lg)
                .focusRequester(focusRequester)
                .clip(HatchWorksTestTheme.shapes.extraExtraLarge),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_for_a_movie),
                    style = HatchWorksTestTheme.typography.lgBold
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    tint = HatchWorksTestTheme.colors.onTertiary,
                    contentDescription = stringResource(R.string.search_for_a_movie),
                    modifier = Modifier.padding(end = xs)
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    query = ""
                    focusManager.clearFocus()
                    onClose()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = HatchWorksTestTheme.colors.onTertiary,
                        contentDescription = stringResource(R.string.search_for_a_movie),
                        modifier = Modifier.padding(end = xs)
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { searchViewModel.searchMovies(query) }),
            colors = TextFieldDefaults.textFieldColors(
                textColor = colors.secondary,
                cursorColor = colors.primary,
                focusedIndicatorColor = colors.onTertiary,
                unfocusedIndicatorColor = colors.darkGray2,
                placeholderColor = colors.onTertiary,
                backgroundColor = colors.tertiary
            ),
            shape = RoundedCornerShape(md)
        )
        MoviesList(
            stateMovies = searchedMoviesStateFlow,
            query = query,
            navigateToMovieDetail = navigateToMovieDetail,
            onClearSearch = {
                query = ""
            }
        )
    }
}

@Composable
private fun MoviesList(
    query: String,
    stateMovies: ResponseState<List<MovieModel>>,
    navigateToMovieDetail: (String, String) -> Unit,
    onClearSearch: () -> Unit
) {
    when (stateMovies) {
        is ResponseState.Loading -> LoadingWheel()
        is ResponseState.Success -> {
            if (stateMovies.response.isEmpty() && query.isNotEmpty()) {
                NoResults(onClearSearch)
            } else {
                GenericMoviesGrid(
                    movies = stateMovies.response,
                    navigateToMovieDetail = navigateToMovieDetail
                )
            }
        }

        else -> {
            // NO_OP
        }
    }
}

@Composable
private fun GenericMoviesGrid(
    movies: List<MovieModel>,
    navigateToMovieDetail: (String, String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(sm),
        verticalArrangement = Arrangement.spacedBy(sm),
        horizontalArrangement = Arrangement.spacedBy(sm)
    ) {
        items(movies.size) { index ->
            GenericMovieItem(movies[index], navigateToMovieDetail)
        }
    }
}

@Composable
private fun NoResults(
    onClearSearch: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.no_results_title),
            style = HatchWorksTestTheme.typography.xlgBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = xl)
        )
        Text(
            text = stringResource(R.string.no_results_description),
            style = HatchWorksTestTheme.typography.mdRegular,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        HatchTertiaryButton(
            text = stringResource(R.string.no_results_button),
            imageVector = Icons.Default.Refresh,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .width(xxxxl)
                .padding(top = xxl)
        ) {
            onClearSearch()
        }
    }
}
