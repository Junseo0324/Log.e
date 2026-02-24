package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.User
import com.devhjs.loge.domain.repository.AuthRepository
import com.devhjs.loge.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(user: User): Result<Unit, Throwable> {
        return try {
            val userId = if (authRepository.isUserLoggedIn()) {
                authRepository.getCurrentUserUid()
            } else null
            
            userRepository.saveUser(user, userId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
