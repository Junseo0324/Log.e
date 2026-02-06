package com.devhjs.loge.data.remote

import com.devhjs.loge.data.dto.OpenAiRequest
import com.devhjs.loge.data.dto.OpenAiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAiService {
    @POST("v1/chat/completions")
    suspend fun getCompletion(
        @Header("Authorization") authorization: String,
        @Body request: OpenAiRequest
    ): OpenAiResponse
}
