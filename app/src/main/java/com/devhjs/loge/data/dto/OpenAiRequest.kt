package com.devhjs.loge.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenAiRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<ApiMessage>,
    @SerialName("max_tokens") val maxTokens: Int = 1000
)