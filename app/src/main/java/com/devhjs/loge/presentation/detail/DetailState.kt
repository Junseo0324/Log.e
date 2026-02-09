package com.devhjs.loge.presentation.detail

import com.devhjs.loge.domain.model.Til

data class DetailState(
    val isLoading: Boolean = false,
    val log: Til? = null,
    val errorMessage: String? = null
)