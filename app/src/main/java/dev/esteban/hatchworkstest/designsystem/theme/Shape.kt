package dev.esteban.hatchworkstest.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.lg
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.md
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.sm
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xl
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xs
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxxl

data class HatchWorksTestShape(
    val card: RoundedCornerShape = RoundedCornerShape(xs),
    val small: RoundedCornerShape = RoundedCornerShape(sm),
    val medium: RoundedCornerShape = RoundedCornerShape(md),
    val large: RoundedCornerShape = RoundedCornerShape(lg),
    val extraLarge: RoundedCornerShape = RoundedCornerShape(xl),
    val extraExtraLarge: RoundedCornerShape = RoundedCornerShape(xxxl),
    val extraLargeEnd: RoundedCornerShape = RoundedCornerShape(bottomEnd = lg, topEnd = lg),
    val extraLargeStart: RoundedCornerShape = RoundedCornerShape(bottomStart = lg, topStart = lg),
)

val LocalHatchWorksTestShape = staticCompositionLocalOf { HatchWorksTestShape() }
