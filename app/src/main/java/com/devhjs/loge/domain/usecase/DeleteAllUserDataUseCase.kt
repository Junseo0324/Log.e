package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.repository.TilRepository
import com.devhjs.loge.domain.repository.UserRepository
import javax.inject.Inject

import com.devhjs.loge.core.util.Result

class DeleteAllUserDataUseCase @Inject constructor(
    private val tilRepository: TilRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit, Exception> {
        return try {
            tilRepository.deleteAll()
            userRepository.deleteAll()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
