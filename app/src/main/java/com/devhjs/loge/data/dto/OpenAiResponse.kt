
package com.devhjs.loge.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class OpenAiResponse(
    val id: String,
    val created: Long,
    val choices: List<Choice>
)