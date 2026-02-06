package com.devhjs.loge.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Choice(
    val index: Int,
    val message: ApiMessage,
    @SerialName("finish_reason") val finishReason: String?
)