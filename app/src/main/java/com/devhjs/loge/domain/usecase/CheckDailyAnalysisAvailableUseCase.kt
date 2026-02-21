package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.AiRepository
import com.devhjs.loge.domain.repository.AuthRepository
import java.time.LocalDate
import javax.inject.Inject

/**
 * 오늘 일일 AI 분석을 사용할 수 있는지 확인하는 UseCase.
 * Supabase ai_daily_usage 테이블에 오늘 날짜 기록이 없으면 true(가능) 반환.
 * 로그인 상태가 아니면 분석 불가 처리.
 */
class CheckDailyAnalysisAvailableUseCase @Inject constructor(
    private val aiRepository: AiRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Boolean, Exception> {
        // 로그인 상태 확인
        val userId = authRepository.getCurrentUserUid()
            ?: return Result.Success(false) // 비로그인 시 분석 불가

        val today = LocalDate.now().toString() // "yyyy-MM-dd"

        return when (val result = aiRepository.checkDailyAnalysisUsed(userId, today)) {
            is Result.Success -> Result.Success(!result.data) // 사용 기록이 없으면 true
            is Result.Error -> Result.Error(result.error)
        }
    }
}
