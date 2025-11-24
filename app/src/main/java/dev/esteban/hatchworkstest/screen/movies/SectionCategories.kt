package dev.esteban.hatchworkstest.screen.movies

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import dev.esteban.hatchworkstest.designsystem.components.HatchTertiaryButton
import dev.esteban.hatchworkstest.designsystem.components.Shimmer
import dev.esteban.hatchworkstest.designsystem.constants.IntValues.INITIAL_0
import dev.esteban.hatchworkstest.designsystem.constants.IntValues.LOADING_ITEMS
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xsm
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxl
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxxl
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestShape
import dev.esteban.movies.domain.model.GenreModel
import dev.esteban.network.ResponseState

@Composable
fun SectionCategories(
    genreState: ResponseState<List<GenreModel>>,
    onClickGenre: (GenreModel) -> Unit
) {
    when (genreState) {
        is ResponseState.Error -> Text("Error")
        is ResponseState.Loading -> SectionCategoriesShimmer()
        is ResponseState.Success -> LazyRow {
            itemsIndexed(genreState.response) { index, genreItem ->
                val cardModifier = when {
                    index == INITIAL_0 -> Modifier.padding(end = xsm)
                    index == genreState.response.lastIndex -> Modifier.padding(start = xsm)
                    else -> Modifier.padding(horizontal = xsm)
                }
                HatchTertiaryButton(
                    text = genreItem.name,
                    modifier = cardModifier
                ) {
                    onClickGenre(genreItem)
                }
            }
        }

        else -> {
            //NO_OP
        }
    }
}

@Composable
private fun SectionCategoriesShimmer() {
    LazyRow {
        items(LOADING_ITEMS) {
            Shimmer(
                modifier = Modifier
                    .clip(LocalHatchWorksTestShape.current.large)
                    .height(xxl)
                    .width(xxxl)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.width(xsm))
        }
    }
}
