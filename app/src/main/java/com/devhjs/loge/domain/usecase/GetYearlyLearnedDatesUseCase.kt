package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.TilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
    operator fun invoke(year: Int): Flow<Result<List<String>, Throwable>> {
        val (start, end) = DateUtils.getYearStartEndTimestamps(year)

        return repository.getAllTil(start, end)
            .map { tils ->
                val dates = tils.map { DateUtils.formatToIsoDate(it.createdAt) }.distinct()
                Result.Success(dates) as Result<List<String>, Throwable>
            }
            .catch { e ->
                emit(Result.Error(e))
            }
            .flowOn(Dispatchers.Default)
    }
}
