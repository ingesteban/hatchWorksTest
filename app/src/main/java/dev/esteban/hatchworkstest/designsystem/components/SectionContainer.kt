package dev.esteban.hatchworkstest.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import dev.esteban.hatchworkstest.R
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.lg
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestTypography

@Composable
fun SectionContainer(
    titleSting: Int,
    navigateToSeeAll: () -> Unit = { },
    showSeeAllButton: Boolean = true,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.padding(all = lg)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(titleSting),
                style = LocalHatchWorksTestTypography.current.lgMedium,
                modifier = Modifier.padding(bottom = lg)
            )

            if (showSeeAllButton) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            navigateToSeeAll()
                        }
                ) {
                    Text(
                        text = stringResource(R.string.see_all),
                        textDecoration = TextDecoration.Underline,
                        style = LocalHatchWorksTestTypography.current.mdMedium
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = stringResource(R.string.see_all),
                    )
                }
            }
        }
        content()
    }
}
