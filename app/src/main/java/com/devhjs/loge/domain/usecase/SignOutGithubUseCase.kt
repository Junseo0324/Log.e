package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * GitHub 로그아웃(연결 해제)을 수행하는 UseCase
 */
class SignOutGithubUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit, Throwable> {
        return try {
            authRepository.signOut()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
