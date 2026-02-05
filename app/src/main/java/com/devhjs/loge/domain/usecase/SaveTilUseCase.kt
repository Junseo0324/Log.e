package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import javax.inject.Inject

class SaveTilUseCase @Inject constructor(
    private val repository: TilRepository,
) {
    suspend operator fun invoke(til: Til): Result<Unit, Throwable> {
        return try {
            repository.saveTil(til)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
