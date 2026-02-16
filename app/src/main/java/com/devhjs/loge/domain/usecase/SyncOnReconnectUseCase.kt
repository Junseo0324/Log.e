package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.network.ConnectivityManager
import com.devhjs.loge.domain.repository.AuthRepository
import com.devhjs.loge.domain.repository.TilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import timber.log.Timber
import javax.inject.Inject

/**
 * 네트워크가 복구(false → true)될 때 Supabase에 자동 동기화하는 UseCase
 * 로그인 상태에서만 TIL + User 데이터를 일괄 동기화
 */
class SyncOnReconnectUseCase @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val tilRepository: TilRepository,
    private val authRepository: AuthRepository
) {
    /**
     * 네트워크 상태를 관찰하여 오프라인 → 온라인 전환 시 동기화 수행
     * scan으로 이전 상태를 추적하여 false → true 전환만 감지
     */
    operator fun invoke(): Flow<Unit> {
        return connectivityManager.observe()
            .scan(Pair(false, false)) { prev, current ->
                // (이전 상태, 현재 상태) 쌍으로 추적
                Pair(prev.second, current)
            }
            .filter { (wasConnected, isConnected) ->
                // 오프라인 → 온라인 전환 시에만 통과
                !wasConnected && isConnected
            }
            .map {
                Timber.d("네트워크 복구 감지 - 동기화 시작")
                syncIfLoggedIn()
            }
    }

    private suspend fun syncIfLoggedIn() {
        if (!authRepository.isUserLoggedIn()) return

        try {
            // TIL 데이터 일괄 동기화
            tilRepository.syncAllTilsToRemote()
            Timber.d("TIL 동기화 완료")
        } catch (e: Exception) {
            Timber.e(e, "TIL 동기화 실패")
        }
    }
}
