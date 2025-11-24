package dev.esteban.hatchworkstest.screen.movies

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.esteban.hatchworkstest.navigation.ScreenNavigation
import dev.esteban.hatchworkstest.screen.moviedetail.MovieDetailNavigation
import dev.esteban.hatchworkstest.screen.moviessearch.MovieSearchNavigation

object MoviesNavigation : ScreenNavigation {
    override val route = "movies"

    @Composable
    override fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry
    ) {
        MoviesScreen(
            navigateToSeeAll = {

            },
            onClickGenre = {

            },
            navigateToSearchMovie = {
                navController.navigate(MovieSearchNavigation.route)
            },
            navigateToMovieDetail = { movieId ->
                navController.navigate(MovieDetailNavigation.movieRoute(movieId))
            }
        )
    }
}

enum class MoviesType {
    Trending, NowPaying, UpComing
}
