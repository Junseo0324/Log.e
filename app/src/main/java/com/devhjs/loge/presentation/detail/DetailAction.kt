package com.devhjs.loge.presentation.detail

sealed interface DetailAction {
    data object OnBackClick : DetailAction
    data class OnEditClick(val id: Long) : DetailAction
    data class OnDeleteClick(val id: Long) : DetailAction
    data class OnDeleteConfirm(val id: Long) : DetailAction
}