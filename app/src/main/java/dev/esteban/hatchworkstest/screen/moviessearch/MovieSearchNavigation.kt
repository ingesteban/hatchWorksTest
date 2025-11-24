package dev.esteban.hatchworkstest.screen.moviessearch

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.esteban.hatchworkstest.navigation.ScreenNavigation
import dev.esteban.hatchworkstest.screen.moviedetail.MovieDetailNavigation

object MovieSearchNavigation : ScreenNavigation {
    override val route = "movie_search"

    @Composable
    override fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry
    ) {
        MoviesSearchScreen(
            navigateToMovieDetail = { movieId ->
                navController.navigate(MovieDetailNavigation.movieRoute(movieId))
            },
            onClose = {
                navController.popBackStack()
            }
        )
    }
}
