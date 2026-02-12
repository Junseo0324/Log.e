package com.devhjs.loge.data.repository

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.devhjs.loge.data.worker.ReminderWorker
import com.devhjs.loge.domain.repository.NotificationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NotificationRepository {

    // 지정된 시간에 알림이 울리도록 ReminderWorker 예약
    override fun scheduleReminder(hour: Int, minute: Int) {

        // 시간 계산
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (target.before(now)) {
            target.add(Calendar.DAY_OF_YEAR, 1)
        }

        // 현재 시점부터 알림이 울릴 시간까지의 밀리초 차이 계산
        val initialDelay = target.timeInMillis - now.timeInMillis


        // 작업 생성
        // OneTimeWorkRequestBuilder : 한 번만 실행되는 작업
        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .addTag("daily_reminder")
            .build()

        /** 작업 등록
         * ExistingWorkPolicy.REPLACE : 만약 이미 예약된 알림이 있다면 취소하고 새로운 알림으로 교체, 중복 알림 방지 역할 수행
         */
        WorkManager.getInstance(context).enqueueUniqueWork(
            "daily_reminder",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    // 알림 설정을 껐을 경우 호출 , 예약되어있던 daily_reminder 작업 취소
    override fun cancelReminder() {
        WorkManager.getInstance(context).cancelUniqueWork("daily_reminder")
    }
}
