package com.devhjs.loge.core.di

import com.devhjs.loge.data.util.RealTimeProvider
import com.devhjs.loge.domain.util.TimeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TimeModule {

    @Binds
    @Singleton
    abstract fun bindTimeProvider(
        realTimeProvider: RealTimeProvider
    ): TimeProvider
}
