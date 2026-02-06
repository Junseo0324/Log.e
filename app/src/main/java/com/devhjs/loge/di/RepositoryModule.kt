package com.devhjs.loge.di

import com.devhjs.loge.data.repository.TilRepositoryImpl
import com.devhjs.loge.domain.repository.TilRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTilRepository(
        tilRepositoryImpl: TilRepositoryImpl
    ): TilRepository
}
