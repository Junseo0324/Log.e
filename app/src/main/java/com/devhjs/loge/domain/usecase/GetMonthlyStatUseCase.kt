package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.model.Stat
import com.devhjs.loge.domain.repository.TilRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMonthlyStatUseCase @Inject constructor(
    private val repository: TilRepository,
) {
    operator fun invoke(month: String): Flow<Stat> {
        return repository.getMonthlyStats(month)
    }
}
