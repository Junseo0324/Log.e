package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.GithubProfile
import com.devhjs.loge.domain.repository.AuthRepository
import javax.inject.Inject

class GetGithubProfileUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Result<GithubProfile, Throwable> {
        return try {
            val profile = GithubProfile(
                name = authRepository.getGithubName(),
                id = authRepository.getGithubId(),
                avatarUrl = authRepository.getGithubAvatarUrl()
            )
            Result.Success(profile)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
