package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * GitHub 로그인을 수행하는 UseCase
 */
class SignInWithGithubUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit, Throwable> {
        return try {
            authRepository.signInWithGithub()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
