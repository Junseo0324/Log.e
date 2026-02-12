package com.devhjs.loge.data.mapper

import com.devhjs.loge.data.local.entity.UserEntity
import com.devhjs.loge.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = this.id,
        name = this.name,
        githubId = this.githubId,
        isNotificationEnabled = this.isNotificationEnabled,
        isDarkModeEnabled = this.isDarkModeEnabled
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.name,
        githubId = this.githubId,
        isNotificationEnabled = this.isNotificationEnabled,
        isDarkModeEnabled = this.isDarkModeEnabled
    )
}
