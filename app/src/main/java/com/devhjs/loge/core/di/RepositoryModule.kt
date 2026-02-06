package com.devhjs.loge.core.di

import com.devhjs.loge.BuildConfig
import com.devhjs.loge.data.repository.AiRepositoryImpl
import com.devhjs.loge.data.repository.MockAiRepositoryImpl
import com.devhjs.loge.data.repository.MockRepositoryImpl
import com.devhjs.loge.data.repository.TilRepositoryImpl
import com.devhjs.loge.domain.repository.AiRepository
import com.devhjs.loge.domain.repository.TilRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTilRepository(
        tilRepositoryImpl: TilRepositoryImpl,
        mockRepositoryImpl: MockRepositoryImpl
    ): TilRepository {
        return if (BuildConfig.FLAVOR == "dev") {
            mockRepositoryImpl
        } else {
            tilRepositoryImpl
        }
    }

    @Provides
    @Singleton
    fun provideAiRepository(
        aiRepositoryImpl: AiRepositoryImpl,
        mockAiRepositoryImpl: MockAiRepositoryImpl
    ): AiRepository {
        return if (BuildConfig.FLAVOR == "dev") {
            mockAiRepositoryImpl
        } else {
            aiRepositoryImpl
        }
    }
}
