package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.AiReport
import com.devhjs.loge.domain.repository.AiRepository
import javax.inject.Inject

class AnalyzeLogUseCase @Inject constructor(
    private val repository: AiRepository
) {
    suspend operator fun invoke(
        title: String,
        learned: String,
        difficult: String
    ): Result<AiReport, Exception> {
        return repository.analyzeLog(title, learned, difficult)
    }
}
