package dev.esteban.hatchworkstest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.esteban.hatchworkstest.screen.moviedetail.MovieDetailNavigation
import dev.esteban.hatchworkstest.screen.movies.MoviesNavigation
import dev.esteban.hatchworkstest.screen.moviespaginated.MoviePaginatedNavigation
import dev.esteban.hatchworkstest.screen.moviessearch.MovieSearchNavigation

interface ScreenNavigation {

    companion object {
        val movies = listOf(
            MoviesNavigation,
            MovieDetailNavigation,
            MovieSearchNavigation,
            MoviePaginatedNavigation,
        )
    }

    val route: String

    val arguments: List<NamedNavArgument> get() = emptyList()

    @Composable
    fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry,
        setTitle: (String?) -> Unit
    )
}
