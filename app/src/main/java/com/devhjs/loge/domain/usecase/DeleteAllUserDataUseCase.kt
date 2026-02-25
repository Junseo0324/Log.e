package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.repository.AuthRepository
import com.devhjs.loge.domain.repository.TilRepository
import com.devhjs.loge.domain.repository.UserRepository
import javax.inject.Inject

import com.devhjs.loge.core.util.Result

class DeleteAllUserDataUseCase @Inject constructor(
    private val tilRepository: TilRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit, Exception> {
        return try {
            tilRepository.deleteAll()
            userRepository.deleteAll()
            if (authRepository.isUserLoggedIn()) {
                authRepository.deleteUser()
                authRepository.signOut()
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
