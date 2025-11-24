package dev.esteban.movies

object ConstantsURL {
    const val NOW_PLAYING = "movie/now_playing"
    const val SEARCH = "search/movie"
    const val TRENDING = "trending/movie/week"
    const val UPCOMING = "movie/upcoming"
    const val POPULAR = "movie/popular"
    const val DISCOVER = "discover/movie"
    const val MOVIE_DETAIL = "movie/{movie_id}"
    const val GENRE_LIST = "genre/movie/list"
}

object ConstantsParameters {
    const val PAGE = "page"
    const val QUERY = "query"
    const val WITH_GENRES = "with_genres"
    const val MOVIE_ID = "movie_id"
}

object ConstantsUtils {
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
}
