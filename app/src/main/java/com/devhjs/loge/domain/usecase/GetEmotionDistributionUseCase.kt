package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.domain.model.ChartPoint
import com.devhjs.loge.domain.model.TilAnalysis
import com.devhjs.loge.domain.repository.TilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

/**
 * TIL 분석 결과를 담는 데이터 클래스
 * 감정 분포 + 난이도 차트 포인트를 함께 반환
 */


/**
 * 월별 TIL 분석 UseCase
 * TIL 목록에서 감정 분포와 난이도 차트 데이터를 집계하여 반환
 */
class GetEmotionDistributionUseCase @Inject constructor(
    private val repository: TilRepository,
) {
    operator fun invoke(month: String): Flow<TilAnalysis> {
        val (start, end) = DateUtils.getMonthStartEndTimestamps(month)

        return repository.getAllTil(start, end).map { tils ->
            // 감정별로 그룹핑하여 각 감정의 빈도 계산
            val emotionDistribution = tils.groupBy { it.emotion }
                .mapValues { (_, tilsForEmotion) -> tilsForEmotion.size }

            // 난이도 차트 포인트: TIL의 createdAt 날짜(day)를 x, difficultyLevel을 y로 매핑
            val difficultyChartPoints = tils
                .sortedBy { it.createdAt }
                .map { til ->
                    val day = Instant.ofEpochMilli(til.createdAt)
                        .atZone(ZoneId.systemDefault())
                        .dayOfMonth
                    ChartPoint(x = day.toFloat(), y = til.difficultyLevel.toFloat())
                }
            TilAnalysis(
                emotionDistribution = emotionDistribution,
                difficultyChartPoints = difficultyChartPoints
            )
        }.flowOn(Dispatchers.Default)
    }
}
