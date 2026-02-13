package com.devhjs.loge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import javax.inject.Inject

/**
 * Hilt를 사용하기 위한 Application 클래스
 * @HiltAndroidApp 어노테이션으로 Hilt 컴포넌트 생성
 */
@HiltAndroidApp
class LogEApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
