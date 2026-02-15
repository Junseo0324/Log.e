package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.Feedback
import com.devhjs.loge.domain.repository.FeedbackRepository
import javax.inject.Inject

class SendFeedbackUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository
) {
    suspend operator fun invoke(feedback: Feedback): Result<Unit, Throwable> {
        return try {
            feedbackRepository.sendFeedback(feedback)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
