package com.devhjs.loge.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.R
import com.devhjs.loge.domain.model.EmotionType
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.presentation.component.CustomButton
import com.devhjs.loge.presentation.component.EmptyLogView
import com.devhjs.loge.presentation.component.LogETopBar
import com.devhjs.loge.presentation.component.LogList
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles


/**
 * HomeScreen - 순수 UI 컴포넌트
 * State와 Action을 외부에서 주입받아 Preview 가능한 형태로 구성
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState= HomeState(),
    onAction: (HomeAction) -> Unit= {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background)
    ) {
        // 상단 앱바
        LogETopBar(
            title = "Log.e",
            titleIcon = R.drawable.ic_home_filled,
            titleStyle = AppTextStyles.JetBrain.Header2,
            actions = {
                CustomButton(
                    modifier = Modifier.width(70.dp),
                    backgroundColor = AppColors.primary,
                    icon = R.drawable.ic_add,
                    contentDescription = "Add Icon",
                    text = "추가",
                    contentColor = AppColors.black,
                    onClick = { onAction(HomeAction.OnAddClick) }
                )
            },
            bottomContent = {
                Text(
                    text = state.currentDate,
                    style = AppTextStyles.Pretendard.Label.copy(color = AppColors.homeLabelTextColor)
                )
                Spacer(modifier = Modifier.width(8.dp))
                VerticalDivider(
                    modifier = Modifier.height(20.dp),
                    thickness = 1.dp,
                    color = AppColors.homeLabelTextColor
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${state.totalLogCount} logs",
                    style = AppTextStyles.Pretendard.Label.copy(color = AppColors.homeLabelTextColor)
                )
            }
        )
        
        // 로딩 상태
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = AppColors.primary)
            }
        } else if (state.logs.isEmpty()) {
            EmptyLogView()
        } else {
            LogList(
                logs = state.logs,
                currentDate = state.currentDate,
                onLogClick = { logId -> 
                    onAction(HomeAction.OnLogClick(logId)) 
                },
                onDeleteClick = { logId ->
                    onAction(HomeAction.OnDeleteClick(logId))
                }
            )
        }
    }
}

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
        onAction = {}
    )
}

// 샘플 데이터 (Preview용)
private val sampleLogs = listOf(
    Til(
        id = 1,
        createdAt = System.currentTimeMillis(),
        title = "Next.js App Router 마이그레이션",
        learned = "Pages Router에서 App Router로 전환하면서 Server Component와 Client Component의 차이를 이해했다.",
        difficult = "",
        emotionScore = 95,
        emotion = EmotionType.FULFILLMENT,
        difficultyLevel = 4,
        updatedAt = System.currentTimeMillis()
    ),
    Til(
        id = 2,
        createdAt = System.currentTimeMillis() - 86400000,
        title = "CSS Flexbox 완벽 정리",
        learned = "justify-content와 align-items의 차이를 확실히 알게 됐다.",
        difficult = "",
        emotionScore = 75,
        emotion = EmotionType.SATISFACTION,
        difficultyLevel = 2,
        updatedAt = System.currentTimeMillis() - 86400000
    )
)