package dev.esteban.hatchworkstest.screen.moviessearch

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.esteban.hatchworkstest.navigation.ScreenNavigation

object MovieSearchNavigation : ScreenNavigation {
    override val route = "movie_search"

    @Composable
    override fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry
    ) {
        MoviesSearchScreen(
            onClose = {
                navController.popBackStack()
            },
            onSearchTriggered = {

            }
        )
    }
}
