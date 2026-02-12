package com.devhjs.loge.domain.repository

interface NotificationRepository {
    fun scheduleReminder(hour: Int, minute: Int)
    fun cancelReminder()
}
