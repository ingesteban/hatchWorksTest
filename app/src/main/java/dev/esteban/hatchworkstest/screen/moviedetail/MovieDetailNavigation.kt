package dev.esteban.hatchworkstest.screen.moviedetail

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.esteban.hatchworkstest.navigation.ScreenNavigation

object MovieDetailNavigation : ScreenNavigation {
    private const val MOVIE_DETAIL = "movie_detail"
    private const val MOVIE_ID = "movie_id"
    override val route = "$MOVIE_DETAIL/{$MOVIE_ID}"

    @Composable
    override fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry,
        setTitle: (String?) -> Unit
    ) {
        setTitle("Movie Detail")
        val movieId = navBackStackEntry.arguments?.getString(MOVIE_ID) ?: ""
        MovieDetailScreen(movieId)
    }

    fun movieRoute(id: String) = "$MOVIE_DETAIL/$id"
}
