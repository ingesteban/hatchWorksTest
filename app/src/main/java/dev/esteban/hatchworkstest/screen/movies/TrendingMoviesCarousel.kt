package dev.esteban.hatchworkstest.screen.movies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import dev.esteban.hatchworkstest.designsystem.components.HatchAsyncImage
import dev.esteban.hatchworkstest.designsystem.components.Shimmer
import dev.esteban.hatchworkstest.designsystem.constants.Float.F0
import dev.esteban.hatchworkstest.designsystem.constants.Float.F0075
import dev.esteban.hatchworkstest.designsystem.constants.Float.F008
import dev.esteban.hatchworkstest.designsystem.constants.Float.F04
import dev.esteban.hatchworkstest.designsystem.constants.Float.F06
import dev.esteban.hatchworkstest.designsystem.constants.Float.F08
import dev.esteban.hatchworkstest.designsystem.constants.Float.F085
import dev.esteban.hatchworkstest.designsystem.constants.Float.F09
import dev.esteban.hatchworkstest.designsystem.constants.Float.F1
import dev.esteban.hatchworkstest.designsystem.constants.IntValues.FRAME_50
import dev.esteban.hatchworkstest.designsystem.constants.IntValues.INITIAL_0
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.lg
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.sm
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xs
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxl
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxxl
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestColors
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestShape
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestTypography
import dev.esteban.movies.domain.model.MovieModel
import dev.esteban.network.ResponseState
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class, ExperimentalMotionApi::class)
@Composable
fun TrendingMoviesCarousel(
    stateMovies: ResponseState<List<MovieModel>>,
    navigateToMovieDetail: (String, String) -> Unit,
) {
    val screenWidth = getScreenWidth()
    val cardWidth = screenWidth * F085
    val cardHeight = cardWidth * F06

    when {
        stateMovies is ResponseState.Success -> {
            val popularMovies = stateMovies.response
            val pagerState =
                rememberPagerState(
                    initialPage = INITIAL_0,
                    pageCount = { popularMovies.size },
                )

            val scrollOffset =
                remember { derivedStateOf { pagerState.currentPage + pagerState.currentPageOffsetFraction } }.value

            val motionScene =
                remember {
                    MotionScene {
                        val cardRef = createRefFor("card")
                        val startConstraint =
                            constraintSet("start") {
                                constrain(cardRef) {
                                    centerTo(parent)
                                    width = Dimension.value(cardWidth)
                                    height = Dimension.value(cardHeight)
                                    scaleX = F1
                                    scaleY = F1
                                }
                            }
                        val endConstraint =
                            constraintSet("end") {
                                constrain(cardRef) {
                                    start.linkTo(parent.start, -(cardWidth * F008))
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    width = Dimension.value(cardWidth)
                                    height = Dimension.value(cardHeight)
                                    scaleX = F085
                                    scaleY = F085
                                }
                            }

                        transition(startConstraint, endConstraint, "default") {
                            keyAttributes(cardRef) {
                                frame(FRAME_50) {
                                    scaleX = F09
                                    scaleY = F09
                                }
                            }
                        }
                    }
                }

            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = xxxl),
                pageSpacing = lg,
                modifier =
                    Modifier
                        .height(cardHeight + xxl)
                        .fillMaxWidth(),
            ) { page ->
                val distance = (scrollOffset - page).absoluteValue
                val progress = distance.coerceIn(F0, F1)
                val movie = popularMovies[page]
                MotionLayout(
                    motionScene = motionScene,
                    progress = progress,
                    modifier =
                        Modifier
                            .width(cardWidth)
                            .height(cardHeight),
                ) {
                    val scale =
                        lerp(
                            start = F1,
                            stop = F085,
                            fraction = progress,
                        )
                    Card(
                        shape = LocalHatchWorksTestShape.current.extraLarge,
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)),
                        elevation = CardDefaults.cardElevation(defaultElevation = sm),
                        modifier =
                            Modifier
                                .clickable {
                                    navigateToMovieDetail(movie.id.toString(), movie.title)
                                }.layoutId("card")
                                .clip(LocalHatchWorksTestShape.current.medium)
                                .fillMaxSize()
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                },
                    ) {
                        Box(contentAlignment = Alignment.BottomCenter) {
                            HatchAsyncImage(
                                path = movie.backdropPath,
                                contentDescription = movie.title,
                                modifier = Modifier.fillMaxSize(),
                            )
                            Text(
                                text = movie.title,
                                style = LocalHatchWorksTestTypography.current.lgMedium,
                                color = LocalHatchWorksTestColors.current.onTertiary,
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .background(
                                            LocalHatchWorksTestColors.current.tertiary.copy(alpha = F08),
                                        ).padding(vertical = xs),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }

        stateMovies is ResponseState.Loading ->
            TrendingMoviesShimmer(
                screenWidth = screenWidth,
                cardWidth = cardWidth,
                cardHeight = cardHeight,
            )
    }
}

@Composable
private fun TrendingMoviesShimmer(
    screenWidth: Dp,
    cardWidth: Dp,
    cardHeight: Dp,
) {
    Box(Modifier.fillMaxWidth()) {
        Shimmer(
            modifier =
                Modifier
                    .height(screenWidth * F04)
                    .width(screenWidth * F0075)
                    .clip(LocalHatchWorksTestShape.current.extraLargeStart)
                    .align(Alignment.CenterEnd),
        )

        Shimmer(
            modifier =
                Modifier
                    .height(cardHeight)
                    .width(cardWidth)
                    .clip(LocalHatchWorksTestShape.current.large)
                    .align(Alignment.Center),
        )

        Shimmer(
            modifier =
                Modifier
                    .height(screenWidth * F04)
                    .width(screenWidth * F0075)
                    .clip(LocalHatchWorksTestShape.current.extraLargeEnd)
                    .align(Alignment.CenterStart),
        )
    }
}

@Composable
private fun getScreenWidth(): Dp {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp.dp
}
