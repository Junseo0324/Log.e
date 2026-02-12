package com.devhjs.loge.data.repository

import com.devhjs.loge.data.local.dao.UserDao
import com.devhjs.loge.data.mapper.toDomain
import com.devhjs.loge.data.mapper.toEntity
import com.devhjs.loge.domain.model.User
import com.devhjs.loge.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override fun getUser(): Flow<User> {
        return userDao.getUser().map { entity ->
            entity?.toDomain() ?: User.DEFAULT
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(user.toEntity())
        }
    }
}
