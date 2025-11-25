package dev.esteban.hatchworkstest

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.esteban.hatchworkstest.designsystem.theme.LocalHatchWorksTestTypography
import dev.esteban.hatchworkstest.navigation.AppNavHost
import dev.esteban.hatchworkstest.screen.moviedetail.MovieDetailNavigation
import dev.esteban.hatchworkstest.screen.movies.MoviesNavigation
import dev.esteban.hatchworkstest.screen.moviespaginated.MoviePaginatedNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HatchWorksTestApp() {
    val titleState = remember { mutableStateOf<String?>(null) }
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            titleState.value?.let { title ->
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = title,
                            style = LocalHatchWorksTestTypography.current.lgBold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                )
            }
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        ) { title ->
            titleState.value = title
        }
    }
}
