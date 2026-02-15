package com.devhjs.loge.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getSessionStatus(): Flow<Boolean> // true: 로그인 됨, false: 로그아웃 됨
    suspend fun signInWithGithub()
    suspend fun signOut()
    fun isUserLoggedIn(): Boolean
    fun getCurrentUserEmail(): String?
    fun getCurrentUserUid(): String?
    fun getGithubName(): String?
    fun getGithubAvatarUrl(): String?
    fun getGithubId(): String?
}
