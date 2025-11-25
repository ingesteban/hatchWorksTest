package dev.esteban.movies.util

enum class MoviesEndpointType {
    NOW_PLAYING,
    TRENDING,
    UPCOMING,
    GENRE,
    NONE
}

fun String.toMoviesEndpointType(): MoviesEndpointType =
    MoviesEndpointType.entries.find { it.name == this } ?: MoviesEndpointType.NONE

