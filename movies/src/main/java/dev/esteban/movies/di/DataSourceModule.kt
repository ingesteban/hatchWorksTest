package dev.esteban.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteban.movies.data.datasource.remote.genres.GenresApi
import dev.esteban.movies.data.datasource.remote.movies.MoviesApi
import dev.esteban.network.RetrofitBuilder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideGenresApi(retrofitBuilder: RetrofitBuilder): GenresApi {
        return retrofitBuilder.create<GenresApi>()
    }

    @Provides
    @Singleton
    fun provideMoviesApi(retrofitBuilder: RetrofitBuilder): MoviesApi {
        return retrofitBuilder.create<MoviesApi>()
    }
}
