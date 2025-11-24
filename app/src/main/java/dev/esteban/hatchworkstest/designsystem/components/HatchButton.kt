package dev.esteban.hatchworkstest.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.md
import dev.esteban.hatchworkstest.designsystem.theme.HatchWorksTestTheme
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestShape
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestTypography

@Composable
fun HatchTertiaryButton(
    text: String,
    textStyle: TextStyle = LocalHatchWorksTestTypography.current.mdMedium,
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = HatchWorksTestTheme.colors.tertiary,
            contentColor = HatchWorksTestTheme.colors.onTertiary
        ),
        modifier = modifier,
        shape = LocalHatchWorksTestShape.current.extraExtraLarge,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = horizontalArrangement,
            modifier = Modifier.fillMaxWidth()
        ) {
            imageVector?.let {
                Icon(
                    imageVector = imageVector,
                    tint = HatchWorksTestTheme.colors.onTertiary,
                    contentDescription = text,
                    modifier = Modifier.padding(end = md)
                )
            }
            Text(
                text = text,
                style = textStyle
            )
        }
    }
}
