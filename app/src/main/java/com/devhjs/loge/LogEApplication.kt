package com.devhjs.loge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Hilt를 사용하기 위한 Application 클래스
 * @HiltAndroidApp 어노테이션으로 Hilt 컴포넌트 생성
 */
@HiltAndroidApp
class LogEApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
