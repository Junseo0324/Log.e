package com.devhjs.loge.data.repository

import com.devhjs.loge.data.local.dao.UserDao
import com.devhjs.loge.data.mapper.toDomain
import com.devhjs.loge.data.mapper.toEntity
import com.devhjs.loge.data.mapper.toRemoteDto
import com.devhjs.loge.domain.model.User
import com.devhjs.loge.domain.repository.UserRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val supabaseClient: SupabaseClient
) : UserRepository {

    override fun getUser(): Flow<User> {
        return userDao.getUser().map { entity ->
            entity?.toDomain() ?: User.DEFAULT
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveUser(user: User, userId: String?) {
        withContext(Dispatchers.IO) {
            // 로컬 DB에 저장
            userDao.insertUser(user.toEntity())

            // 로그인 상태라면 원격 DB에도 동기화
            if (userId != null) {
                launch {
                    try {
                        supabaseClient.from("users").upsert(user.toRemoteDto(userId))
                    } catch (e: Exception) {
                        // 원격 동기화 실패 시 로컬 데이터는 유지
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            userDao.deleteAll()
        }
    }
}
