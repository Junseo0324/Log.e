package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.MonthlyDashboardData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMonthlyDashboardUseCase @Inject constructor(
    private val getMonthlyStatUseCase: GetMonthlyStatUseCase,
    private val getEmotionDistributionUseCase: GetEmotionDistributionUseCase,
    private val getMonthlyReviewUseCase: GetMonthlyReviewUseCase
) {
    operator fun invoke(month: String): Flow<Result<MonthlyDashboardData, Throwable>> = flow {
        emitAll(
            combine(
                getMonthlyStatUseCase(month),
                getEmotionDistributionUseCase(month),
                flow {
                    emit(getMonthlyReviewUseCase(month, forceFetchFromAi = false))
                }
            ) { stat, tilAnalysis, reviewResult ->
                val data = MonthlyDashboardData(
                    stat = stat,
                    emotionDistribution = tilAnalysis.emotionDistribution,
                    difficultyChartPoints = tilAnalysis.difficultyChartPoints,
                    aiReport = (reviewResult as? Result.Success)?.data
                )
                Result.Success(data) as Result<MonthlyDashboardData, Throwable>
            }
        )
    }.catch { e ->
        emit(Result.Error(e))
    }
}
