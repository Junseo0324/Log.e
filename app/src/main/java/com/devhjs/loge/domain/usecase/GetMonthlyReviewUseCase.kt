package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.AiReport
import com.devhjs.loge.domain.repository.AiRepository
import com.devhjs.loge.domain.repository.AuthRepository
import com.devhjs.loge.domain.repository.TilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

import javax.inject.Inject

class GetMonthlyReviewUseCase @Inject constructor(
    private val aiRepository: AiRepository,
    private val tilRepository: TilRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        month: String,
        forceFetchFromAi: Boolean = true,
        forceRefresh: Boolean = false // true시 캐시 무시하고 AI 재호출 (광고 시청 후 재분석)
    ): Result<AiReport?, Exception> = withContext(Dispatchers.IO) {
        try {
            // forceRefresh가 false일 때만 저장된 분석 결과 반환 (캐시 우선)
            if (!forceRefresh) {
                val savedReviewResult = aiRepository.getSavedMonthlyReview(month)
                if (savedReviewResult is Result.Success && savedReviewResult.data != null) {
                    return@withContext Result.Success(savedReviewResult.data)
                }
            }

            if (!forceFetchFromAi) {
                return@withContext Result.Success(null)
            }

            val (start, end) = DateUtils.getMonthStartEndTimestamps(month)
            val tils = tilRepository.getAllTil(start, end).first()

            if (tils.isEmpty()) {
                return@withContext Result.Success(
                    AiReport(
                        date = System.currentTimeMillis(),
                        emotion = "분석 불가",
                        emotionScore = 0,
                        difficultyLevel = "-",
                        comment = "이번 달에 작성된 회고가 없어 분석할 수 없습니다. 조금 더 꾸준히 기록해 보세요!"
                    )
                )
            }

            val emotions = tils.map { it.emotion.label }
            val scores = tils.map { it.emotionScore }
            val difficulties = tils.map { it.difficultyLevel }

            // AI 분석 요청
            val aiResult = aiRepository.getAiFeedback(month, emotions, scores, difficulties)

            // 분석 성공 시 저장
            if (aiResult is Result.Success) {
                val userId = authRepository.getCurrentUserUid()
                if (userId != null) {
                    aiRepository.saveMonthlyReview(userId, month, aiResult.data)
                }
            }

            aiResult
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
