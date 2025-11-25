package dev.esteban.hatchworkstest.screen.moviedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.esteban.hatchworkstest.R
import dev.esteban.hatchworkstest.designsystem.components.GenericErrorScreen
import dev.esteban.hatchworkstest.designsystem.components.HatchAsyncImage
import dev.esteban.hatchworkstest.designsystem.components.HatchTertiaryButton
import dev.esteban.hatchworkstest.designsystem.components.LabelValueText
import dev.esteban.hatchworkstest.designsystem.components.LoadingWheel
import dev.esteban.hatchworkstest.designsystem.constants.IntValues.INITIAL_0
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.lg
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.md
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xsm
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxxl2
import dev.esteban.hatchworkstest.designsystem.theme.HatchWorksTestTheme
import dev.esteban.movies.domain.model.GenreModel
import dev.esteban.movies.domain.model.MovieDetailModel
import dev.esteban.movies.domain.model.ProductionCompanyModel
import dev.esteban.movies.presentation.viewmodel.MovieViewModel
import dev.esteban.network.ResponseState

@Composable
fun MovieDetailScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    movieId: String
) {
    val stateMovieDetail = viewModel.movieDetailStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.movieDetail(movieId)
    }
    MovieDetailContent(stateMovieDetail.value) {
        viewModel.movieDetail(movieId)
    }
}

@Composable
fun MovieDetailContent(
    stateMovieDetail: ResponseState<MovieDetailModel>,
    onRetryClick: () -> Unit
) {
    when (stateMovieDetail) {
        is ResponseState.Error -> GenericErrorScreen(onRetryClick = onRetryClick)
        is ResponseState.Loading -> LoadingWheel()
        is ResponseState.Success -> {
            val movieDetail = stateMovieDetail.response
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                HatchAsyncImage(
                    path = movieDetail.posterPath,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .height(600.dp)
                        .fillMaxWidth()
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(600.dp))

                    Column(
                        modifier = Modifier
                            .background(HatchWorksTestTheme.colors.background) // Usa el color de fondo del tema
                            .padding(horizontal = lg) // Aplica tu padding horizontal
                            .fillMaxWidth()
                    ) {
                        CategoriesRow(genres = movieDetail.genres)

                        Text(
                            text = movieDetail.title,
                            style = HatchWorksTestTheme.typography.xlgBold,
                            modifier = Modifier.padding(bottom = md)
                        )

                        movieDetail.overview?.let { overview ->
                            Text(
                                text = stringResource(R.string.overview),
                                style = HatchWorksTestTheme.typography.lgBold,
                                modifier = Modifier.padding(bottom = md)
                            )
                            Text(
                                text = overview,
                                style = HatchWorksTestTheme.typography.smRegular,
                                modifier = Modifier.padding(bottom = md)
                            )
                        }

                        Text(
                            text = stringResource(R.string.key_details),
                            style = HatchWorksTestTheme.typography.xlgBold,
                            modifier = Modifier.padding(vertical = md)
                        )

                        movieDetail.releaseDate?.let { releaseDate ->
                            LabelValueText(R.string.release, releaseDate)
                        }

                        LabelValueText(R.string.status, movieDetail.status)

                        LabelValueText(R.string.popularity, movieDetail.popularity.toString())

                        ProductionCompanies(movieDetail.productionCompanies)

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }

            }
        }

        else -> {
            //NO_OP
        }
    }
}

@Composable
private fun CategoriesRow(genres: List<GenreModel>) {
    LazyRow {
        itemsIndexed(genres) { index, genreItem ->
            val cardModifier = when {
                index == INITIAL_0 -> Modifier.padding(end = xsm)
                index == genres.lastIndex -> Modifier.padding(start = xsm)
                else -> Modifier.padding(horizontal = xsm)
            }
            HatchTertiaryButton(
                text = genreItem.name,
                modifier = cardModifier.padding(vertical = md)
            ) {
                // NO_OP
            }
        }
    }
}

@Composable
private fun ProductionCompanies(productionCompanies: List<ProductionCompanyModel>) {
    if (productionCompanies.isNotEmpty()) {
        Column {
            Text(
                text = stringResource(R.string.companies),
                style = HatchWorksTestTheme.typography.xlgBold,
                modifier = Modifier.padding(vertical = md)
            )
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                productionCompanies.forEach { company ->
                    Column(
                        modifier = Modifier
                            .padding(end = lg)
                            .width(xxxl2)
                            .background(HatchWorksTestTheme.colors.background)
                    ) {

                        HatchAsyncImage(
                            path = company.logoPath,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(xxxl2)
                                .clip(shape = HatchWorksTestTheme.shapes.extraExtraLarge),
                        )
                        Text(
                            text = company.name,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
