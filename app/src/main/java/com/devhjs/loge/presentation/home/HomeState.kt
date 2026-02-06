package com.devhjs.loge.presentation.home

import com.devhjs.loge.domain.model.Til

/**
 * HomeScreen의 UI 상태를 관리하는 데이터 클래스
 * 모든 UI 상태를 하나의 클래스로 모아서 관리 (MVI 패턴)
 */
data class HomeState(
    val isLoading: Boolean = false,
    val logs: List<Til> = emptyList(),
    val currentDate: String = "",
    val totalLogCount: Int = 0,
    val errorMessage: String? = null
)
