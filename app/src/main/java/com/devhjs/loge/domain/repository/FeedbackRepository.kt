package com.devhjs.loge.domain.repository

import com.devhjs.loge.domain.model.Feedback

interface FeedbackRepository {
    suspend fun sendFeedback(feedback: Feedback)
}
