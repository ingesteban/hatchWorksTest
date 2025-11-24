package dev.esteban.hatchworkstest.screen.moviessearch

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import dev.esteban.hatchworkstest.R
import dev.esteban.hatchworkstest.designsystem.constants.Spacing.xs
import dev.esteban.hatchworkstest.designsystem.theme.HatchWorksTestTheme
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestColors
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestTypography
import kotlinx.coroutines.delay

@Composable
fun MoviesSearchScreen(
    onClose: () -> Unit,
    onSearchTriggered: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var query by remember { mutableStateOf("") }
    val colors = LocalHatchWorksTestColors.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(query) {
        if (query.length >= 2) {
            delay(350)
            onSearchTriggered(query)
        }
    }

    TextField(
        value = query,
        onValueChange = { query = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester)
            .clip(RoundedCornerShape(50.dp)),
        placeholder = {
            Text(
                text = stringResource(R.string.search_for_a_movie),
                style = LocalHatchWorksTestTypography.current.lgBold
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                tint = HatchWorksTestTheme.colors.onTertiary,
                contentDescription = stringResource(R.string.search_for_a_movie),
                modifier = Modifier.padding(end = xs)
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                query = ""
                focusManager.clearFocus()
                onClose()
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = HatchWorksTestTheme.colors.onTertiary,
                    contentDescription = stringResource(R.string.search_for_a_movie),
                    modifier = Modifier.padding(end = xs)
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearchTriggered(query) }),
        colors = TextFieldDefaults.textFieldColors(
            textColor = colors.secondary,
            cursorColor = colors.primary,
            focusedIndicatorColor = colors.onTertiary,
            unfocusedIndicatorColor = colors.darkGray2,
            placeholderColor = colors.onTertiary,
            backgroundColor = colors.tertiary
        ),
        shape = RoundedCornerShape(12.dp)
    )
}
