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
    suspend operator fun invoke(month: String): Result<AiReport, Exception> = withContext(Dispatchers.IO) {
        try {
            // 1. 저장된 회고가 있는지 먼저 확인
            val savedReviewResult = aiRepository.getSavedMonthlyReview(month)
            if (savedReviewResult is Result.Success && savedReviewResult.data != null) {
                return@withContext Result.Success(savedReviewResult.data)
            }

            // 2. 저장된 회고가 없으면 기존 분석 로직 수행
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

            // 3. AI 분석 요청
            val aiResult = aiRepository.getAiFeedback(month, emotions, scores, difficulties)

            // 4. 분석 성공 시 저장
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
