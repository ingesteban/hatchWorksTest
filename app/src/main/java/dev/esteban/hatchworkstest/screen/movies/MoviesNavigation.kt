package dev.esteban.hatchworkstest.screen.movies

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.esteban.hatchworkstest.navigation.ScreenNavigation
import dev.esteban.hatchworkstest.screen.moviedetail.MovieDetailNavigation

object MoviesNavigation : ScreenNavigation {
    override val route = "movies"

    @Composable
    override fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry
    ) {
        MoviesScreen { movieId ->
            navController.navigate(MovieDetailNavigation.movieRoute(movieId))
        }
    }
}
