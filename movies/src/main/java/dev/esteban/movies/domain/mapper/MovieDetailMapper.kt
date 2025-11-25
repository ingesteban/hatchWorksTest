package dev.esteban.movies.domain.mapper

import dev.esteban.movies.ConstantsUtils.IMAGE_URL_BACKDROP
import dev.esteban.movies.ConstantsUtils.IMAGE_URL_POSTER
import dev.esteban.movies.data.datasource.remote.model.NetworkCollectionInfoResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkGenreResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkMovieDetailResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkProductionCompanyResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkProductionCountryResponse
import dev.esteban.movies.data.datasource.remote.model.NetworkSpokenLanguageResponse
import dev.esteban.movies.domain.model.CollectionInfoModel
import dev.esteban.movies.domain.model.GenreModel
import dev.esteban.movies.domain.model.MovieDetailModel
import dev.esteban.movies.domain.model.ProductionCompanyModel
import dev.esteban.movies.domain.model.ProductionCountryModel
import dev.esteban.movies.domain.model.SpokenLanguageModel
import dev.esteban.network.Mapper
import dev.esteban.network.ResponseState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailMapper
    @Inject
    constructor() : Mapper<NetworkMovieDetailResponse, ResponseState<MovieDetailModel>> {
        override fun apply(input: NetworkMovieDetailResponse): ResponseState<MovieDetailModel> =
            ResponseState.Success(mapDetailToDomain(input))

        fun mapDetailToDomain(input: NetworkMovieDetailResponse): MovieDetailModel =
            MovieDetailModel(
                adult = input.adult,
                backdropPath = IMAGE_URL_BACKDROP + input.backdropPath,
                belongsToCollection = input.belongsToCollection?.let { mapCollectionInfo(it) },
                budget = input.budget,
                genres = input.genres.map { mapGenre(it) },
                homepage = input.homepage,
                id = input.id,
                imdbId = input.imdbId,
                originCountry = input.originCountry,
                originalLanguage = input.originalLanguage,
                originalTitle = input.originalTitle,
                overview = input.overview,
                popularity = input.popularity,
                posterPath = IMAGE_URL_POSTER + input.posterPath,
                productionCompanies = input.productionCompanies.map { mapProductionCompany(it) },
                productionCountries = input.productionCountries.map { mapProductionCountry(it) },
                releaseDate = input.releaseDate,
                revenue = input.revenue,
                runtime = input.runtime,
                spokenLanguages = input.spokenLanguages.map { mapSpokenLanguage(it) },
                status = input.status,
                tagline = input.tagline,
                title = input.title,
                video = input.video,
                voteAverage = input.voteAverage,
                voteCount = input.voteCount,
            )

        private fun mapCollectionInfo(input: NetworkCollectionInfoResponse): CollectionInfoModel =
            CollectionInfoModel(
                id = input.id,
                name = input.name,
                posterPath = IMAGE_URL_POSTER + input.posterPath,
                backdropPath = IMAGE_URL_BACKDROP + input.backdropPath,
            )

        private fun mapProductionCompany(input: NetworkProductionCompanyResponse): ProductionCompanyModel =
            ProductionCompanyModel(
                id = input.id,
                logoPath = IMAGE_URL_POSTER + input.logoPath,
                name = input.name,
                originCountry = input.originCountry,
            )

        private fun mapGenre(input: NetworkGenreResponse): GenreModel =
            GenreModel(
                id = input.id,
                name = input.name,
            )

        private fun mapProductionCountry(input: NetworkProductionCountryResponse): ProductionCountryModel =
            ProductionCountryModel(
                iso = input.iso,
                name = input.name,
            )

        private fun mapSpokenLanguage(input: NetworkSpokenLanguageResponse): SpokenLanguageModel =
            SpokenLanguageModel(
                englishName = input.englishName,
                iso = input.iso,
                name = input.name,
            )
    }
