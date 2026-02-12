package com.devhjs.loge.domain.repository

import com.devhjs.loge.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<User>
    suspend fun saveUser(user: User)
    suspend fun deleteAll()
}
