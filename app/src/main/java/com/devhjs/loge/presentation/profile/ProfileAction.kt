package com.devhjs.loge.presentation.profile

/**
 * ProfileScreen에서 발생하는 사용자 액션 (MVI 패턴)
 */
sealed interface ProfileAction {
    data class OnNameChange(val name: String) : ProfileAction
    data object OnSaveClick : ProfileAction
    data object OnBackClick : ProfileAction
    data object OnGithubLoginClick : ProfileAction
    data object OnGithubDisconnectClick : ProfileAction
}
