package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SyncGithubProfileUseCase @Inject constructor(
    private val getGithubProfileUseCase: GetGithubProfileUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase
) {
    suspend operator fun invoke(): Result<Unit, Throwable> {
        val profileResult = getGithubProfileUseCase()

        if (profileResult is Result.Success) {
            val profile = profileResult.data
            val githubName = profile.name
            val githubAvatarUrl = profile.avatarUrl
            val githubId = profile.id

            // GetUserUseCase 호출하여 현재 유저 확인
            val userResult = getUserUseCase().firstOrNull()
            
            if (userResult is Result.Success) {
                val currentUser = userResult.data
                val displayName = githubName ?: githubId ?: currentUser.name

                val updatedUser = currentUser.copy(
                    name = displayName,
                    avatarUrl = githubAvatarUrl,
                    githubId = githubId ?: currentUser.githubId
                )

                return when (val saveResult = saveUserUseCase(updatedUser)) {
                    is Result.Success -> Result.Success(Unit)
                    is Result.Error -> Result.Error(saveResult.error)
                }
            } else {
                 return Result.Error(Exception("Local user not found"))
            }

        } else if (profileResult is Result.Error) {
            return Result.Error(profileResult.error)
        }
        
        return Result.Error(Exception("Unknown error"))
    }
}
