package com.devhjs.loge.domain.network

import kotlinx.coroutines.flow.Flow

/**
 * 네트워크 연결 상태를 관찰하는 인터페이스
 * Domain 레이어에 위치하여 Android 의존성 없이 사용 가능
 */
interface ConnectivityManager {
    /**
     * 네트워크 연결 상태를 Flow로 방출
     * @return true = 연결됨, false = 연결 안 됨
     */
    fun observe(): Flow<Boolean>
}
