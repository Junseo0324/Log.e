package com.devhjs.loge.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.devhjs.loge.MainActivity
import com.devhjs.loge.R
import com.devhjs.loge.domain.service.NotificationService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NotificationService {

    /**
     * 알림을 띄우는 함수
     */
    override fun showDailyReminder() {

        // 알림 매니저 가져오기
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "loge_reminder_channel"

        // 채널 생성 Oreo 이에서는 채널을 만들어야 함.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(channelId) == null) {
                val channel = NotificationChannel(
                    channelId,
                    "Daily Reminder",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Reminder to write TIL"
                }
                notificationManager.createNotificationChannel(channel)
            }
        }

        /**
         * 클릭 동작 설정
         *  Intent.FLAG_ACTIVITY_CLEAR_TASK : 앱을 완전히 재시작
         */
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 알림 구성 코드
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_home_filled)
            .setContentTitle("오늘의 배움을 기록하셨나요? \uD83D\uDCDD")
            .setContentText("Log.e와 함께 하루를 정리해보세요!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1001, notification)
    }
}
