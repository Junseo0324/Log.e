package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.Feedback
import com.devhjs.loge.domain.repository.AuthRepository
import com.devhjs.loge.domain.repository.FeedbackRepository
import javax.inject.Inject

class SendFeedbackUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(feedback: Feedback): Result<Unit, Throwable> {
        return try {
            val userId = if (authRepository.isUserLoggedIn()) {
                authRepository.getCurrentUserUid()
            } else null

            feedbackRepository.sendFeedback(feedback, userId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
