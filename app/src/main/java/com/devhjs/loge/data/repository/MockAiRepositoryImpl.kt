package com.devhjs.loge.data.repository

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.AiReport
import com.devhjs.loge.domain.repository.AiRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class MockAiRepositoryImpl @Inject constructor() : AiRepository {
    override suspend fun getAiFeedback(
        date: String,
        emotions: List<String>,
        scores: List<Int>,
        difficulties: List<Int>
    ): Result<AiReport, Exception> {
        delay(1000)
        return Result.Success(
            AiReport(
                date = System.currentTimeMillis(),
                emotion = "성취감",
                emotionScore = 5,
                difficultyLevel = "어려움",
                comment = "AI 피드백 (Mock): 이번 달은 꾸준히 성장하는 모습을 보여주셨어요! 어려운 내용도 잘 소화하고 계시네요."
            )
        )
    }

    override suspend fun analyzeLog(
        title: String,
        learned: String,
        difficult: String
    ): Result<AiReport, Exception> {
        delay(1000)
        return Result.Success(
            AiReport(
                date = System.currentTimeMillis(),
                emotion = "성취감",
                emotionScore = 95,
                difficultyLevel = "보통",
                comment = "AI 분석 (Mock): 훌륭합니다! 꾸준히 학습하는 모습이 매우 인상적이에요."
            )
        )
    }

    override suspend fun saveMonthlyReview(
        userId: String,
        yearMonth: String,
        report: AiReport
    ): Result<Unit, Exception> {
        return Result.Success(Unit)
    }

    override suspend fun getSavedMonthlyReview(yearMonth: String): Result<AiReport?, Exception> {
        return Result.Success(null)
    }
}
