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
        navBackStackEntry: NavBackStackEntry,
        setTitle: (String?) -> Unit,
    ) {
        setTitle(null)
        MoviesSearchScreen(
            navigateToMovieDetail = { movieId, movieTitle ->
                navController.navigate(MovieDetailNavigation.movieRoute(movieId, movieTitle))
            },
            onClose = {
                navController.popBackStack()
            },
        )
    }
}
