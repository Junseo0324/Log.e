package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import com.devhjs.loge.domain.util.WidgetUpdateManager
import javax.inject.Inject

class SaveTilUseCase @Inject constructor(
    private val repository: TilRepository,
    private val widgetUpdateManager: WidgetUpdateManager
) {
    suspend operator fun invoke(til: Til): Result<Unit, Throwable> {
        return try {
            repository.saveTil(til)
            widgetUpdateManager.updateAllWidgets()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
