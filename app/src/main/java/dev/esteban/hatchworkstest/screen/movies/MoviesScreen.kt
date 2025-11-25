package dev.esteban.hatchworkstest.screen.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.esteban.hatchworkstest.R
import dev.esteban.hatchworkstest.designsystem.components.HatchTertiaryButton
import dev.esteban.hatchworkstest.designsystem.components.SectionContainer
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.lg
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxl2
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestTypography
import dev.esteban.movies.presentation.viewmodel.HomeViewModel
import dev.esteban.movies.util.MoviesEndpointType

@Composable
fun MoviesScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToMoviesPaginated: (MoviesEndpointType, String?, String?) -> Unit,
    navigateToMovieDetail: (String) -> Unit,
    navigateToSearchMovie: () -> Unit,
) {
    val homeMoviesStateFlow by viewModel.homeMoviesStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadMovies()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HatchTertiaryButton(
            text = stringResource(R.string.search_for_a_movie),
            textStyle = LocalHatchWorksTestTypography.current.lgBold,
            imageVector = Icons.Outlined.Search,
            modifier = Modifier
                .padding(lg)
                .height(55.dp)
                .fillMaxWidth()
                .height(xxl2)
        ) {
            navigateToSearchMovie()
        }

        TrendingMoviesCarousel(
            stateMovies = homeMoviesStateFlow.popular,
            navigateToMovieDetail = navigateToMovieDetail
        )

        SectionContainer(
            titleSting = R.string.categories,
            showSeeAllButton = false
        ) {
            SectionCategories(
                genreState = homeMoviesStateFlow.genres,
                onClickGenre = { genreModel ->
                    navigateToMoviesPaginated(MoviesEndpointType.GENRE, genreModel.id.toString(), genreModel.name)
                }
            )
        }

        SectionContainer(
            titleSting = R.string.trending,
            navigateToSeeAll = {
                navigateToMoviesPaginated(MoviesEndpointType.TRENDING, null, null)
            }
        ) {
            SectionMoviesContent(
                stateMovies = homeMoviesStateFlow.trending,
                navigateToMovieDetail = navigateToMovieDetail
            )
        }

        SectionContainer(
            titleSting = R.string.now_playing,
            navigateToSeeAll = {
                navigateToMoviesPaginated(MoviesEndpointType.NOW_PLAYING, null, null)
            }
        ) {
            SectionMoviesContent(
                stateMovies = homeMoviesStateFlow.nowPlaying,
                navigateToMovieDetail = navigateToMovieDetail
            )
        }

        SectionContainer(
            titleSting = R.string.upcoming,
            navigateToSeeAll = {
                navigateToMoviesPaginated(MoviesEndpointType.UPCOMING, null, null)
            }
        ) {
            SectionMoviesContent(
                stateMovies = homeMoviesStateFlow.upcoming,
                navigateToMovieDetail = navigateToMovieDetail
            )
        }
    }
}
