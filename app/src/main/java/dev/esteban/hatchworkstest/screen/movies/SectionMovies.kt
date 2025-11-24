package dev.esteban.hatchworkstest.screen.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import coil.compose.AsyncImage
import dev.esteban.hatchworkstest.designsystem.components.Shimmer
import dev.esteban.hatchworkstest.designsystem.constants.IntValues.INITIAL_0
import dev.esteban.hatchworkstest.designsystem.constants.IntValues.LOADING_ITEMS
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.lg
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.sm
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxxxl
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxxxxl
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestColors
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestShape
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestTypography
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.network.ResponseState

@Composable
fun SectionMoviesContent(
    stateMovies: ResponseState<List<MovieModel>>,
    navigateToMovieDetail: (String) -> Unit,
) {
    when (stateMovies) {
        is ResponseState.Error -> Text("Error")
        is ResponseState.Loading -> SectionMoviesShimmer()
        is ResponseState.Success -> {
            LazyRow {
                itemsIndexed(stateMovies.response) { index, movieItem ->
                    val cardModifier = when {
                        index == INITIAL_0 -> Modifier
                            .width(xxxxl)
                            .padding(end = sm)

                        index == stateMovies.response.lastIndex -> Modifier
                            .width(xxxxl)
                            .padding(start = sm)

                        else -> Modifier
                            .width(xxxxl)
                            .padding(horizontal = sm)
                    }
                    Card(modifier = cardModifier.clickable {
                        navigateToMovieDetail(movieItem.id.toString())
                    }) {
                        Column(modifier = Modifier.background(LocalHatchWorksTestColors.current.background)) {
                            val annotatedString = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontStyle = LocalHatchWorksTestTypography.current.smMedium.fontStyle,
                                        color = LocalHatchWorksTestColors.current.primary
                                    )
                                ) {
                                    append(movieItem.voteCount.toString())
                                }
                                append(" (${movieItem.voteAverage})")
                            }
                            AsyncImage(
                                model = movieItem.posterPath,
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(sm))
                                    .width(xxxxl)
                                    .height(xxxxxl)
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(sm)
                            ) {
                                Text(
                                    text = movieItem.title,
                                    style = LocalHatchWorksTestTypography.current.mdRegular,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.Star,
                                        contentDescription = null,
                                        tint = LocalHatchWorksTestColors.current.primary,
                                        modifier = Modifier.size(lg)
                                    )
                                    Text(
                                        text = annotatedString,
                                        style = LocalHatchWorksTestTypography.current.smRegular
                                    )
                                }
                            }
                        }
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
private fun SectionMoviesShimmer() {
    LazyRow {
        items(LOADING_ITEMS) {
            Shimmer(
                modifier = Modifier
                    .clip(LocalHatchWorksTestShape.current.large)
                    .height(xxxxxl)
                    .width(xxxxl)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.width(sm))
        }
    }
}
