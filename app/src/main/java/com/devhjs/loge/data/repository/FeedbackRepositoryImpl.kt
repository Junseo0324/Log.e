package com.devhjs.loge.data.repository

import com.devhjs.loge.data.dto.FeedbackRemoteDto
import com.devhjs.loge.domain.model.Feedback
import com.devhjs.loge.domain.repository.AuthRepository
import com.devhjs.loge.domain.repository.FeedbackRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedbackRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val authRepository: AuthRepository
) : FeedbackRepository {

    override suspend fun sendFeedback(feedback: Feedback) {
        withContext(Dispatchers.IO) {
            // 로그인 상태라면 사용자 ID 포함
            val userId = if (authRepository.isUserLoggedIn()) {
                authRepository.getCurrentUserUid()
            } else {
                null
            }

            val dto = FeedbackRemoteDto(
                type = feedback.type.value,
                title = feedback.title,
                content = feedback.content,
                userId = userId
            )

            supabaseClient.from("feedbacks").insert(dto)
        }
    }
}
