package dev.esteban.hatchworkstest.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.esteban.hatchworkstest.R
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.md
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xl
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xxxl
import dev.esteban.hatchworkstest.designsystem.theme.HatchWorksTestTheme

@Composable
fun GenericErrorScreen(
    title: String = stringResource(R.string.error_title_default),
    message: String = stringResource(R.string.error_message_default),
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(xl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Error",
            tint = HatchWorksTestTheme.colors.error,
            modifier = Modifier.size(xxxl)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = title,
            style = HatchWorksTestTheme.typography.lgBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = message,
            style = HatchWorksTestTheme.typography.mdMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        HatchTertiaryButton(
            text = stringResource(R.string.button_retry),
            modifier = Modifier.padding(vertical = md),
            horizontalArrangement = Arrangement.Center,
            onClick = onRetryClick
        )
    }
}
