package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.domain.repository.TilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 연간 학습 날짜 목록을 가져오는 UseCase
 * ContributionGraph(GitHub 잔디)에 사용
 */
class GetYearlyLearnedDatesUseCase @Inject constructor(
    private val repository: TilRepository,
) {
    operator fun invoke(year: Int): Flow<List<String>> {
        val (start, end) = DateUtils.getYearStartEndTimestamps(year)

        return repository.getAllTil(start, end).map { tils ->
            tils.map { DateUtils.formatToIsoDate(it.createdAt) }.distinct()
        }.flowOn(Dispatchers.Default)
    }
}
