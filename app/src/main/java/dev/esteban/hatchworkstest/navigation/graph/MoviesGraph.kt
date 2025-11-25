package dev.esteban.hatchworkstest.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.esteban.hatchworkstest.navigation.ScreenNavigation

fun NavGraphBuilder.moviesGraph(
    navController: NavHostController,
    setTitle: (String?) -> Unit
) {
    ScreenNavigation.movies.forEach { screen ->
        composable(
            screen.route,
            screen.arguments
        ) { navBackStackEntry ->
            screen.Content(navController, navBackStackEntry, setTitle)
        }
    }
}
