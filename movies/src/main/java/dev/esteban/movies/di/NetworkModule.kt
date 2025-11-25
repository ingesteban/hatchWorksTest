package dev.esteban.movies.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteban.movies.data.datasource.remote.genres.GenresApi
import dev.esteban.movies.data.datasource.remote.genres.GenresDataSource
import dev.esteban.movies.data.datasource.remote.genres.GenresDataSourceImpl
import dev.esteban.movies.data.datasource.remote.movies.MoviesDataSource
import dev.esteban.movies.data.datasource.remote.movies.MoviesDataSourceImpl
import dev.esteban.network.RetrofitBuilder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
    @Binds
    fun bindsMoviesDataSource(moviesDataSourceImpl: MoviesDataSourceImpl): MoviesDataSource

    @Binds
    fun bindsGenresDataSource(genresDataSourceImpl: GenresDataSourceImpl): GenresDataSource
}
