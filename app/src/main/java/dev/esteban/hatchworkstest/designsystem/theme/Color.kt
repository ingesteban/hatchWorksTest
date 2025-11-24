package dev.esteban.hatchworkstest.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF000C21)
val AccentYellow = Color(0xFFFACC15)
val PureWhite = Color(0xFFEEEEEE)
val LightGrayText = Color(0xFFB0B0B0)
val Black = Color(0xFF000000)
val NearBlack = Color(0xFF222222)
val MediumGrayText = Color(0xFF6A6A6A)
val LightGray = Color(0xFFDDDDDD)
val DarkGray = Color(0xFF1E1E1E)

val LightPurple = Color(0xFFE4DFF5)
val DarkPurple = Color(0xFF1E1A34)
val AccentGreen = Color(0xFF6FC905)


val LightPurple10 = Color(0xFFE4DFF5)
val DarkPurple10 = Color(0xFF3E21C9)


data class HatchWorksTestColors(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val background: Color,
    val darkGray2: Color
)

val DarkColors = HatchWorksTestColors(
    primary = AccentYellow,
    onPrimary = Black,
    secondary = PureWhite,
    tertiary = DarkPurple,
    onTertiary = LightPurple10,
    background = DarkBackground,
    darkGray2 = LightGrayText
)

val LightColors = HatchWorksTestColors(
    primary = AccentGreen,
    onPrimary = Black,
    secondary = NearBlack,
    tertiary = LightPurple,
    onTertiary = DarkPurple10,
    background = PureWhite,
    darkGray2 = MediumGrayText
)

val lightColorScheme = lightColorScheme(
    primary = LightColors.primary,
    onPrimary = LightColors.onPrimary,
    secondary = LightColors.secondary,
    tertiary = LightColors.tertiary,
    onTertiary = LightColors.onTertiary,
    background = LightColors.background,
    surface = PureWhite,
    onSurface = NearBlack,
)

val darkColorScheme = darkColorScheme(
    primary = DarkColors.primary,
    onPrimary = DarkColors.onPrimary,
    secondary = DarkColors.secondary,
    tertiary = DarkColors.tertiary,
    onTertiary = DarkColors.onTertiary,
    background = DarkColors.background,
    surface = DarkGray,
    onSurface = PureWhite,
)

val LocalHatchWorksTestColors = staticCompositionLocalOf { LightColors }