package com.devhjs.loge.domain.repository

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.AiReport

interface AiRepository {
    suspend fun getAiFeedback(
        date: String,
        emotions: List<String>,
        scores: List<Int>,
        difficulties: List<Int>
    ): Result<AiReport, Exception>

    suspend fun analyzeLog(
        title: String,
        learned: String,
        difficult: String
    ): Result<AiReport, Exception>

    suspend fun saveMonthlyReview(
        userId: String,
        yearMonth: String,
        report: AiReport
    ): Result<Unit, Exception>

    suspend fun getSavedMonthlyReview(
        yearMonth: String
    ): Result<AiReport?, Exception>

    // 오늘 일일 AI 분석을 이미 사용했는지 서버에서 확인
    suspend fun checkDailyAnalysisUsed(userId: String, today: String): Result<Boolean, Exception>

    // 오늘 일일 AI 분석 사용 기록을 서버에 저장
    suspend fun markDailyAnalysisUsed(userId: String, today: String): Result<Unit, Exception>
}
