package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.TilRepository
import javax.inject.Inject

class SyncTilsUseCase @Inject constructor(
    private val repository: TilRepository
) {
    suspend operator fun invoke(): Result<Unit, Exception> {
        return try {
            repository.syncAllTilsToRemote()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
