package dev.esteban.hatchworkstest.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.SubcomposeAsyncImage
import dev.esteban.hatchworkstest.R
import dev.esteban.hatchworkstest.designsystem.constants.Float.F06
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.md
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxl3
import dev.esteban.hatchworkstest.designsystem.theme.HatchWorksTestTheme

@Composable
fun HatchAsyncImage(
    path: String?,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
    modifier: Modifier = Modifier,
) {
    SubcomposeAsyncImage(
        model = path,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
        loading = { LoadingWheel() },
        error = { HatchNoImage() },
    )
}

@Composable
private fun HatchNoImage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    HatchWorksTestTheme.colors.tertiary.copy(F06),
                ),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_no_image),
            contentDescription = null,
            tint = HatchWorksTestTheme.colors.onTertiary,
            modifier =
                Modifier
                    .width(xxl3),
        )
        Text(
            text = stringResource(R.string.no_image_provided),
            color = HatchWorksTestTheme.colors.onTertiary,
            style = HatchWorksTestTheme.typography.mdBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = md),
        )
    }
}
