package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTodayLogUseCase @Inject constructor(
    private val repository: TilRepository
) {
    operator fun invoke(): Flow<Til?> {
        val (start, end) = DateUtils.getTodayStartEnd()
        return repository.getAllTil(start, end).map { logs ->
            logs.firstOrNull()
        }
    }
}
