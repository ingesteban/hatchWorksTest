package dev.esteban.hatchworkstest.screen.moviedetail

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.esteban.hatchworkstest.R
import dev.esteban.hatchworkstest.navigation.ScreenNavigation
import dev.esteban.hatchworkstest.screen.TopBarState

object MovieDetailNavigation : ScreenNavigation {
    private const val MOVIE_DETAIL = "movie_detail"
    private const val MOVIE_ID = "movie_id"
    override val route = "$MOVIE_DETAIL/{$MOVIE_ID}"

    @Composable
    override fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry
    ) {
        val movieId = navBackStackEntry.arguments?.getString(MOVIE_ID) ?: "no content get"
        MovieDetailScreen(movieId)
    }

    override val topBar = TopBarState(R.string.movie_detail, true)

    fun movieRoute(id: String) = "$MOVIE_DETAIL/$id"
}
