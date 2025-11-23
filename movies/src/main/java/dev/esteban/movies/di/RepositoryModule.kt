package dev.esteban.movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteban.movies.data.repository.GenresRepositoryImpl
import dev.esteban.movies.data.repository.MoviesRepositoryImpl
import dev.esteban.movies.domain.repository.GenresRepository
import dev.esteban.movies.domain.repository.MoviesRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsMoviesRepository(
        moviesRepository: MoviesRepositoryImpl
    ): MoviesRepository

    @Binds
    fun bindsGenresRepository(
        genresRepository: GenresRepositoryImpl
    ): GenresRepository
}
