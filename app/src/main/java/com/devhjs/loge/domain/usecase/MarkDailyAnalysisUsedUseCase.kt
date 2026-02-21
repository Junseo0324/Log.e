package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.AiRepository
import com.devhjs.loge.domain.repository.AuthRepository
import java.time.LocalDate
import javax.inject.Inject

/**
 * 일일 AI 분석 완료 후, 오늘 날짜를 Supabase ai_daily_usage 테이블에 기록하는 UseCase.
 * 로그인 상태가 아니면 조용히 성공 처리 (기록만 생략).
 */
class MarkDailyAnalysisUsedUseCase @Inject constructor(
    private val aiRepository: AiRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit, Exception> {
        val userId = authRepository.getCurrentUserUid()
            ?: return Result.Success(Unit) // 비로그인 시 기록 생략

        val today = LocalDate.now().toString() // "yyyy-MM-dd"

        return aiRepository.markDailyAnalysisUsed(userId, today)
    }
}
