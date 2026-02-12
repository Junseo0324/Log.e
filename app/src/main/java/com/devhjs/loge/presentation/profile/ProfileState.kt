package com.devhjs.loge.presentation.profile

import com.devhjs.loge.domain.model.User

/**
 * ProfileScreen의 UI 상태를 관리하는 데이터 클래스 (MVI 패턴)
 */
data class ProfileState(
    val user: User = User.DEFAULT,
    val isLoading: Boolean = false
)
