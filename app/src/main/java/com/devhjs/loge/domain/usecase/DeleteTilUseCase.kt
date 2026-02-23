package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import com.devhjs.loge.domain.util.WidgetUpdateManager
import timber.log.Timber
import javax.inject.Inject

class DeleteTilUseCase @Inject constructor(
    private val repository: TilRepository,
    private val widgetUpdateManager: WidgetUpdateManager
) {
    suspend operator fun invoke(id: Long): Result<Unit, Throwable> {
        Timber.d("DeleteTilUseCase: Deleting log with id $id")
        return try {
            repository.deleteTil(id)
            Timber.d("DeleteTilUseCase: Successfully deleted log $id")
            widgetUpdateManager.updateAllWidgets()
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "DeleteTilUseCase: Failed to delete log $id")
            Result.Error(e)
        }
    }
}