package com.devhjs.loge.domain.repository

interface AuthRepository {
    suspend fun signInWithGithub()
    suspend fun signOut()
    fun isUserLoggedIn(): Boolean
    fun getCurrentUserEmail(): String?
    fun getCurrentUserUid(): String?
    fun getGithubName(): String?
    fun getGithubAvatarUrl(): String?
    fun getGithubId(): String?
}
