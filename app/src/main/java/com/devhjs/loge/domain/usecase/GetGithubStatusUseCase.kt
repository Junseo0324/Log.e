package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.model.GithubStatus
import com.devhjs.loge.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * GitHub 연결 상태 정보를 조회하는 UseCase
 * 반환값: GithubStatus (연결 여부, username, avatarUrl)
 */
class GetGithubStatusUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): GithubStatus {
        val isConnected = authRepository.isUserLoggedIn()
        return if (isConnected) {
            GithubStatus(
                isConnected = true,
                username = authRepository.getGithubId(),
                avatarUrl = authRepository.getGithubAvatarUrl()
            )
        } else {
            GithubStatus(
                isConnected = false,
                username = null,
                avatarUrl = null
            )
        }
    }
}
