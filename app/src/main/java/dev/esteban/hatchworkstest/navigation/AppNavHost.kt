package dev.esteban.hatchworkstest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.esteban.hatchworkstest.navigation.graph.moviesGraph
import dev.esteban.hatchworkstest.screen.movies.MoviesNavigation

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController,
    setTitle: (String?) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = MoviesNavigation.route,
        modifier = modifier,
    ) {
        moviesGraph(navController, setTitle)
    }
}
