package dev.esteban.hatchworkstest.screen.movies

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.esteban.hatchworkstest.R
import dev.esteban.hatchworkstest.navigation.ScreenNavigation
import dev.esteban.hatchworkstest.screen.TopBarState
import dev.esteban.hatchworkstest.screen.moviedetail.MovieDetailNavigation

object MoviesNavigation : ScreenNavigation {
    override val route = "movies"
    override val topBar = TopBarState(R.string.movies)

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
