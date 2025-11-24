package dev.esteban.hatchworkstest.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.esteban.hatchworkstest.designsystem.constants.Float.F05
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.sm
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xl
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xs
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxxl
import dev.esteban.hatchworkstest.designsystem.theme.HatchWorksTestTheme

@Composable
fun LoadingWheel() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HatchWorksTestTheme.colors.background.copy(alpha = F05))
    ) {
        CircularLoading()
    }
}

@Composable
fun CircularLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .size(xxxl)
                .align(Alignment.Center)
                .padding(sm),
            shape = RoundedCornerShape(sm)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.padding(xl),
                color = HatchWorksTestTheme.colors.onTertiary,
                strokeWidth = xs
            )
        }
    }
}
