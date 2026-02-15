package com.devhjs.loge.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRemoteDto(
    @SerialName("user_id")
    val userId: String, // Supabase Auth UID
    @SerialName("name")
    val name: String,
    @SerialName("github_id")
    val githubId: String,
    @SerialName("email")
    val email: String? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    @SerialName("is_notification_enabled")
    val isNotificationEnabled: Boolean,
    @SerialName("is_dark_mode_enabled")
    val isDarkModeEnabled: Boolean,
    @SerialName("notification_hour")
    val notificationHour: Int? = null,
    @SerialName("notification_minute")
    val notificationMinute: Int? = null
)
