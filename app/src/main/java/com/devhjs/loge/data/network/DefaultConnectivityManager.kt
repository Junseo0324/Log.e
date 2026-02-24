package com.devhjs.loge.data.network

import android.content.Context
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.devhjs.loge.domain.network.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

/**
 * Android ConnectivityManager를 사용한 네트워크 연결 상태 옵저버 구현체
 * callbackFlow를 통해 네트워크 상태 변화를 Flow<Boolean>로 방출
 */
class DefaultConnectivityManager @Inject constructor(
    @ApplicationContext private val context: Context
) : ConnectivityManager {

    override fun observe(): Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService(
            android.net.ConnectivityManager::class.java
        )

        // 초기 상태 방출
        val isCurrentlyConnected = connectivityManager.activeNetwork?.let { network ->
            connectivityManager.getNetworkCapabilities(network)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } ?: false
        trySend(isCurrentlyConnected)

        val callback = object : NetworkCallback() {
            // 네트워크 연결됨
            override fun onAvailable(network: Network) {
                trySend(true)
            }

            // 네트워크 끊김
            override fun onLost(network: Network) {
                trySend(false)
            }

            // 네트워크 기능 변경
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                val hasInternet = networkCapabilities
                    .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                trySend(hasInternet)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)

        // Flow가 취소되면 콜백 해제
        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()
}
