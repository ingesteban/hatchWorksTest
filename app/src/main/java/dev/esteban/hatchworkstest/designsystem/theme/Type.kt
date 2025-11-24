package dev.esteban.hatchworkstest.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.esteban.hatchworkstest.R

private val RobotoFontFamily = FontFamily(
    Font(R.font.roboto_thin, FontWeight.Thin),
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_black, FontWeight.Black)
)

val xlgBoldTypography: TextStyle = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
)

val lgBoldTypography: TextStyle = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
)

val lgMediumTypography: TextStyle = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
)

val mdMediumTypography: TextStyle = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
)

val mdBoldTypography: TextStyle = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
)

val mdRegularTypography: TextStyle = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
)

val smMediumTypography: TextStyle = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
)

val smRegularTypography: TextStyle = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
)


data class HatchWorksTestTypography(
    val xlgBold: TextStyle = xlgBoldTypography,
    val lgBold: TextStyle = lgBoldTypography,
    val lgMedium: TextStyle = lgMediumTypography,
    val mdBold: TextStyle = mdBoldTypography,
    val mdMedium: TextStyle = mdMediumTypography,
    val mdRegular: TextStyle = mdMediumTypography,
    val smRegular: TextStyle = smRegularTypography,
    val smMedium: TextStyle = smMediumTypography,
)

internal val LocalHatchWorksTestTypography = staticCompositionLocalOf { HatchWorksTestTypography() }
