package com.devhjs.loge.core.di

import com.devhjs.loge.data.util.RealWidgetUpdateManager
import com.devhjs.loge.domain.util.WidgetUpdateManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WidgetModule {

    @Binds
    @Singleton
    abstract fun bindWidgetUpdateManager(
        realWidgetUpdateManager: RealWidgetUpdateManager
    ): WidgetUpdateManager
}
