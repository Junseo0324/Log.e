package com.devhjs.loge.domain.model

data class User(
    val id: Long = 1L,
    val name: String,
    val githubId: String,
    val email: String? = null,
    val avatarUrl: String? = null,
    val isNotificationEnabled: Boolean,
    val isDarkModeEnabled: Boolean,
    val notificationTime: Pair<Int, Int>? = null
) {
    companion object {
        val DEFAULT = User(
            id = 1L,
            name = "Developer",
            githubId = "username",
            email = null,
            avatarUrl = null,
            isNotificationEnabled = true,
            isDarkModeEnabled = true,
            notificationTime = Pair(21, 0)
        )
    }
}
