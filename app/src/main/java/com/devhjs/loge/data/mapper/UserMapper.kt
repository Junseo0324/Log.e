package com.devhjs.loge.data.mapper

import com.devhjs.loge.data.local.entity.UserEntity
import com.devhjs.loge.domain.model.User

fun UserEntity.toDomain(): User {
    val notificationTimePair = if (this.notificationTime != null) {
        val parts = this.notificationTime.split(":")
        if (parts.size == 2) {
            Pair(parts[0].toInt(), parts[1].toInt())
        } else {
            null
        }
    } else {
        null
    }

    return User(
        id = this.id,
        name = this.name,
        githubId = this.githubId,
        isNotificationEnabled = this.isNotificationEnabled,
        isDarkModeEnabled = this.isDarkModeEnabled,
        notificationTime = notificationTimePair
    )
}

fun User.toEntity(): UserEntity {
    val notificationTimeString = this.notificationTime?.let {
        "%02d:%02d".format(it.first, it.second)
    }

    return UserEntity(
        id = this.id,
        name = this.name,
        githubId = this.githubId,
        isNotificationEnabled = this.isNotificationEnabled,
        isDarkModeEnabled = this.isDarkModeEnabled,
        notificationTime = notificationTimeString
    )
}
