package dev.esteban.hatchworkstest.designsystem.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.md
import dev.esteban.hatchworkstest.designsystem.theme.HatchWorksTestTheme

@Composable
fun LabelValueText(
    label: Int,
    value: String,
) {
    val releaseDateAnnotated =
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(stringResource(label))
            }
            append(": $value")
        }

    Text(
        text = releaseDateAnnotated,
        style = HatchWorksTestTheme.typography.smRegular,
        modifier = Modifier.padding(bottom = md),
    )
}
