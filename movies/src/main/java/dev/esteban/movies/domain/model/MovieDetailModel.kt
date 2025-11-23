package dev.esteban.movies.domain.model

data class MovieDetailModel(
    val adult: Boolean,
    val backdropPath: String?,
    val belongsToCollection: CollectionInfoModel?,
    val budget: Int,
    val genres: List<GenreModel>,
    val homepage: String?,
    val id: Int,
    val imdbId: String?,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompanyModel>,
    val productionCountries: List<ProductionCountryModel>,
    val releaseDate: String?,
    val revenue: Long,
    val runtime: Int?,
    val spokenLanguages: List<SpokenLanguageModel>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

data class CollectionInfoModel(
    val id: Int,
    val name: String,
    val posterPath: String?,
    val backdropPath: String?
)

data class GenreModel(
    val id: Int,
    val name: String
)

data class ProductionCompanyModel(
    val id: Int,
    val logoPath: String?,
    val name: String,
    val originCountry: String
)

data class ProductionCountryModel(
    val iso: String,
    val name: String
)

data class SpokenLanguageModel(
    val englishName: String,
    val iso: String,
    val name: String
)
