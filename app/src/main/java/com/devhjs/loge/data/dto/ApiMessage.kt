package com.devhjs.loge.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiMessage(
    val role: String,
    val content: String
)
