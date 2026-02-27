package com.devhjs.loge

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devhjs.loge.domain.usecase.SyncOnReconnectUseCase
import com.devhjs.loge.presentation.designsystem.LogETheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.handleDeeplinks
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.isSystemInDarkTheme
import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.usecase.GetUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var supabaseClient: SupabaseClient

    @Inject
    lateinit var syncOnReconnectUseCase: SyncOnReconnectUseCase

    @Inject
    lateinit var getUserUseCase: GetUserUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.navigationBars())
        }

        // GitHub OAuth 리다이렉트 처리
        supabaseClient.handleDeeplinks(intent)

        // 네트워크 복구 시 Supabase 자동 동기화 (포그라운드에서만 동작)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                syncOnReconnectUseCase().collect {}
            }
        }

        setContent {
            val userResult by getUserUseCase().collectAsState(initial = null)
            val isDarkTheme = when (val result = userResult) {
                is Result.Success -> result.data.isDarkModeEnabled
                else -> isSystemInDarkTheme()
            }

            LogETheme(darkTheme = isDarkTheme) {
                LogEApp(
                    onNavigateToOnboarding = {
                        val intent = Intent(this@MainActivity, MainActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // 앱이 이미 실행 중일 때 deep link 처리
        supabaseClient.handleDeeplinks(intent)
    }
}

