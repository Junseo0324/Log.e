package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.domain.repository.TilRepository
import com.devhjs.loge.domain.util.TimeProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWeeklyTilCountUseCase @Inject constructor(
    private val tilRepository: TilRepository,
    private val timeProvider: TimeProvider
) {
    operator fun invoke(): Flow<Int> {
        val now = timeProvider.getCurrentDate()
        val (startTimestamp, endTimestamp) = DateUtils.getWeekStartEnd(now)
        
        return tilRepository.getAllTil(startTimestamp, endTimestamp).map { tils ->
            tils.size
        }
    }
}
