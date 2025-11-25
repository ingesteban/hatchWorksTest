package dev.esteban.hatchworkstest.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun HatchWorksTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: HatchWorksTestTypography = HatchWorksTestTheme.typography,
    colors: HatchWorksTestColors = HatchWorksTestTheme.colors,
    shapes: HatchWorksTestShape = HatchWorksTestTheme.shapes,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme
    val customColors = if (darkTheme) DarkColors else LightColors

    val appShapes = Shapes(
        extraSmall = HatchWorksTestTheme.shapes.small,
        small = HatchWorksTestTheme.shapes.medium,
        medium = HatchWorksTestTheme.shapes.medium,
        large = HatchWorksTestTheme.shapes.large,
        extraLarge = HatchWorksTestTheme.shapes.extraLarge
    )
    CompositionLocalProvider(
        LocalHatchWorksTestTypography provides typography,
        LocalHatchWorksTestColors provides customColors,
        LocalHatchWorksTestShape provides shapes
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = appShapes,
            content = content
        )
    }
}

object HatchWorksTestTheme {
    val typography: HatchWorksTestTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalHatchWorksTestTypography.current

    val colors: HatchWorksTestColors
        @Composable
        @ReadOnlyComposable
        get() = LocalHatchWorksTestColors.current

    val shapes: HatchWorksTestShape
        @Composable
        @ReadOnlyComposable
        get() = LocalHatchWorksTestShape.current
}
