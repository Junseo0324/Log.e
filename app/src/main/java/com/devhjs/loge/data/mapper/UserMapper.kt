package com.devhjs.loge.data.mapper

import com.devhjs.loge.data.dto.UserRemoteDto
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
        email = this.email,
        avatarUrl = this.avatarUrl,
        isNotificationEnabled = this.isNotificationEnabled,
        isDarkModeEnabled = this.isDarkModeEnabled,
        notificationTime = notificationTimePair
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.name,
        githubId = this.githubId,
        email = this.email,
        avatarUrl = this.avatarUrl,
        isNotificationEnabled = this.isNotificationEnabled,
        isDarkModeEnabled = this.isDarkModeEnabled,
    )
}

fun User.toRemoteDto(userId: String): UserRemoteDto {
    return UserRemoteDto(
        userId = userId,
        name = this.name,
        githubId = this.githubId,
        email = this.email,
        avatarUrl = this.avatarUrl,
        isNotificationEnabled = this.isNotificationEnabled,
        isDarkModeEnabled = this.isDarkModeEnabled,
        notificationHour = this.notificationTime?.first,
        notificationMinute = this.notificationTime?.second
    )
}

fun UserRemoteDto.toDomain(): User {
    return User(
        name = this.name,
        githubId = this.githubId,
        email = this.email,
        avatarUrl = this.avatarUrl,
        isNotificationEnabled = this.isNotificationEnabled,
        isDarkModeEnabled = this.isDarkModeEnabled,
        notificationTime = if (this.notificationHour != null && this.notificationMinute != null) {
            Pair(this.notificationHour, this.notificationMinute)
        } else {
            null
        }
    )
}
