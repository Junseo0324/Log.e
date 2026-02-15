package com.devhjs.loge.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRemoteDto(
    @SerialName("user_id")
    val userId: String, // Supabase Auth UID
    @SerialName("name")
    val name: String,
    @SerialName("user_name") val githubId: String,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    @SerialName("notification_status") val isNotificationEnabled: Boolean,
    @SerialName("is_dark_mode_enabled")
    val isDarkModeEnabled: Boolean,
    @SerialName("notification_hour")
    val notificationHour: Int? = null,
    @SerialName("notification_minute")
    val notificationMinute: Int? = null
)
