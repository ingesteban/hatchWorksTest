package dev.esteban.hatchworkstest.designsystem.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.esteban.hatchworkstest.designsystem.theme.LightGray

private const val SHIMMER_DURATION = 2500
private const val SHIMMER_COLOR_ALPHA_06 = 0.6f
private const val SHIMMER_COLOR_ALPHA_02 = 0.2f

@Composable
fun Shimmer(modifier: Modifier) {

    val colorLightGrey = LightGray

    val shimmerColors = listOf(
        colorLightGrey.copy(alpha = SHIMMER_COLOR_ALPHA_06),
        colorLightGrey.copy(alpha = SHIMMER_COLOR_ALPHA_02),
        colorLightGrey.copy(alpha = SHIMMER_COLOR_ALPHA_06),
    )

    val infiniteTransition = rememberInfiniteTransition()
    val translateAnimation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = SHIMMER_DURATION),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(modifier = modifier.clip(RoundedCornerShape(4.dp))) {
        BoxWithConstraints(modifier = Modifier.matchParentSize()) {
            val widthPx = with(LocalDensity.current) { this@BoxWithConstraints.maxWidth.toPx() }
            val shimmerWidthPx = widthPx * 1f

            val brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(
                    x = translateAnimation.value * (widthPx + shimmerWidthPx) - shimmerWidthPx,
                    y = 0f
                ),
                end = Offset(
                    x = translateAnimation.value * (widthPx + shimmerWidthPx),
                    y = 0f
                )
            )

            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .background(brush)
            )
        }
    }
}
