package dev.esteban.hatchworkstest.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import dev.esteban.hatchworkstest.designsystem.constants.Float.F08
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xs
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxxxl2
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestColors
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestTypography
import dev.esteban.movies.domain.model.MovieModel

@Composable
fun GenericMovieItem(
    movie: MovieModel,
    navigateToMovieDetail: (String, String) -> Unit,
) {
    Card(
        modifier = Modifier.clickable {
            navigateToMovieDetail(movie.id.toString(), movie.title)
        }
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.background(LocalHatchWorksTestColors.current.background)
        ) {
            HatchAsyncImage(
                path = movie.posterPath,
                modifier = Modifier.height(xxxxl2)
            )
            Text(
                text = movie.title,
                style = LocalHatchWorksTestTypography.current.lgMedium,
                color = LocalHatchWorksTestColors.current.onTertiary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        LocalHatchWorksTestColors.current.tertiary.copy(alpha = F08)
                    )
                    .padding(vertical = xs),
                textAlign = TextAlign.Center
            )
        }
    }
}
