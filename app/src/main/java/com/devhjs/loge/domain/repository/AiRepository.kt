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
}
