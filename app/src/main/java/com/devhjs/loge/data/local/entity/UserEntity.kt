package com.devhjs.loge.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: Long = 1L,
    val name: String,
    val githubId: String,
    val avatarUrl: String? = null,
    val isNotificationEnabled: Boolean,
    val isDarkModeEnabled: Boolean,
    val notificationTime: String? = "21:00"
)
