package dev.esteban.hatchworkstest.screen.movies

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.esteban.hatchworkstest.navigation.ScreenNavigation
import dev.esteban.hatchworkstest.screen.moviedetail.MovieDetailNavigation
import dev.esteban.hatchworkstest.screen.moviespaginated.MoviePaginatedNavigation
import dev.esteban.hatchworkstest.screen.moviessearch.MovieSearchNavigation

object MoviesNavigation : ScreenNavigation {
    override val route = "movies"

    @Composable
    override fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry,
        setTitle: (String?) -> Unit,
    ) {
        setTitle(null)
        MoviesScreen(
            navigateToMoviesPaginated = { type, genreId, genreName ->
                navController.navigate(
                    MoviePaginatedNavigation.moviesPaginatedRoute(
                        type.name,
                        genreId,
                        genreName,
                    ),
                )
            },
            navigateToSearchMovie = {
                navController.navigate(MovieSearchNavigation.route)
            },
            navigateToMovieDetail = { movieId, movieName ->
                navController.navigate(MovieDetailNavigation.movieRoute(movieId, movieName))
            },
        )
    }
}
