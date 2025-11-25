package dev.esteban.hatchworkstest.screen.moviedetail

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.esteban.hatchworkstest.navigation.ScreenNavigation

object MovieDetailNavigation : ScreenNavigation {
    private const val MOVIE_DETAIL = "movie_detail"
    private const val MOVIE_ID = "movie_id"
    private const val MOVIE_TITLE = "movie_title"
    override val route = "$MOVIE_DETAIL/{$MOVIE_ID}/{$MOVIE_TITLE}"

    @Composable
    override fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry,
        setTitle: (String?) -> Unit,
    ) {
        val movieId = navBackStackEntry.arguments?.getString(MOVIE_ID) ?: ""
        val movieTitle = navBackStackEntry.arguments?.getString(MOVIE_TITLE) ?: ""
        setTitle(movieTitle)
        MovieDetailScreen(movieId = movieId)
    }

    fun movieRoute(
        id: String,
        title: String,
    ) = "$MOVIE_DETAIL/$id/$title"
}
