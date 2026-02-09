package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import timber.log.Timber
import javax.inject.Inject

class DeleteTilUseCase @Inject constructor(
    private val repository: TilRepository
) {
    suspend operator fun invoke(til: Til): Result<Unit, Throwable> {
        Timber.d("DeleteTilUseCase: Deleting log with id ${til.id}")
        return try {
            repository.deleteTil(til)
            Timber.d("DeleteTilUseCase: Successfully deleted log ${til.id}")
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "DeleteTilUseCase: Failed to delete log ${til.id}")
            Result.Error(e)
        }
    }
}