package com.devhjs.loge.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devhjs.loge.presentation.component.HomeAppBar
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.home.component.LogList

/**
 * HomeScreen - 순수 UI 컴포넌트
 * State와 Action을 외부에서 주입받아 Preview 가능한 형태로 구성
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    action: HomeAction,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background)
    ) {
        // 상단 앱바
        HomeAppBar(
            currentDate = state.currentDate,
            logCount = state.totalLogCount,
            onAddClick = action.onAddClick
        )
        
        // 로딩 상태
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = AppColors.primary)
            }
        } else {
            // 로그 리스트 (날짜별 그룹)
            LogList(
                logs = state.logs,
                onLogClick = action.onLogClick
            )
        }
    }
}


// ========== Preview ==========

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        state = HomeState(
            isLoading = false,
            currentDate = "2026.02.05",
            totalLogCount = 5,
            logs = sampleLogs
        ),
        action = HomeAction(
            onAddClick = {},
            onLogClick = {},
            onRefresh = {}
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenLoadingPreview() {
    HomeScreen(
        state = HomeState(
            isLoading = true,
            currentDate = "2026.02.05",
            totalLogCount = 0
        ),
        action = HomeAction(
            onAddClick = {},
            onLogClick = {},
            onRefresh = {}
        )
    )
}

// 샘플 데이터 (Preview용)
private val sampleLogs = listOf(
    LogItem(
        id = 1,
        emotion = EmotionType.HAPPY,
        emotionScore = 88,
        time = "오전 08:15",
        title = "Next.js App Router 마이그레이션",
        content = "Pages Router에서 App Router로 전환하면서 Server Component와 Client Component의 차이를 이해했다.",
        level = 4,
        date = "2026.02.05",
        dayOfWeek = "목",
        isToday = true
    ),
    LogItem(
        id = 2,
        emotion = EmotionType.HAPPY,
        emotionScore = 80,
        time = "오전 04:45",
        title = "CSS Flexbox 완벽 정리",
        content = "justify-content와 align-items의 차이를 확실히 알게 됐다. flex-direction에 따라 main axis와 cross axis가 바뀐다.",
        level = 2,
        date = "2026.02.04",
        dayOfWeek = "수",
        isToday = false
    ),
    LogItem(
        id = 3,
        emotion = EmotionType.CONFUSED,
        emotionScore = 62,
        time = "오전 06:40",
        title = "Docker 컨테이너 이해하기",
        content = "Docker로 개발 환경을 격리할 수 있다는 점이 인상 깊었다. Dockerfile 작성법과 docker-compose를 배웠다.",
        level = 4,
        date = "2026.02.04",
        dayOfWeek = "수",
        isToday = false
    ),
    LogItem(
        id = 4,
        emotion = EmotionType.STRUGGLE,
        emotionScore = 55,
        time = "오전 07:00",
        title = "비동기 처리와 Promise",
        content = "비동기 처리의 개념을 이해하고 Promise와 async/await 문법을 학습했다.",
        level = 5,
        date = "2026.02.03",
        dayOfWeek = "화",
        isToday = false
    )
)