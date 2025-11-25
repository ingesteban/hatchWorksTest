package dev.esteban.hatchworkstest.screen.moviespaginated

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.esteban.hatchworkstest.R
import dev.esteban.hatchworkstest.navigation.ScreenNavigation
import dev.esteban.hatchworkstest.screen.moviedetail.MovieDetailNavigation
import dev.esteban.movies.util.MoviesEndpointType
import dev.esteban.movies.util.toMoviesEndpointType

object MoviePaginatedNavigation : ScreenNavigation {

    private const val MOVIES_PAGINATED = "movies_paginated"
    private const val TYPE = "type"
    private const val GENRE_ID = "genreId"
    private const val GENRE_NAME = "genreName"

    override val route = "$MOVIES_PAGINATED/{$TYPE}?$GENRE_ID={$GENRE_ID}&$GENRE_NAME={$GENRE_NAME}"

    @Composable
    override fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry,
        setTitle: (String?) -> Unit
    ) {
        val type = (navBackStackEntry.arguments?.getString(TYPE) ?: "").toMoviesEndpointType()
        val genresId = navBackStackEntry.arguments?.getString(GENRE_ID)
        val genresName = navBackStackEntry.arguments?.getString(GENRE_NAME)
        var title = genresName
        if (title == null) {
            title = stringResource(getTitle(type))
        }
        setTitle(title)

        MoviesPaginatedScreen(
            type = type,
            genres = genresId,
            navigateToMovieDetail = { movieId, movieTitle ->
                navController.navigate(MovieDetailNavigation.movieRoute(movieId, movieTitle))
            },
        )
    }

    fun moviesPaginatedRoute(
        type: String,
        genreId: String? = null,
        genreName: String? = null
    ): String {
        return if (genreId == null) {
            "$MOVIES_PAGINATED/$type"
        } else {
            "$MOVIES_PAGINATED/$type?$GENRE_ID=$genreId&$GENRE_NAME=$genreName"
        }
    }

    private fun getTitle(type: MoviesEndpointType): Int = when (type) {
        MoviesEndpointType.NOW_PLAYING -> R.string.now_playing
        MoviesEndpointType.TRENDING -> R.string.trending
        else -> R.string.now_playing
    }
}
