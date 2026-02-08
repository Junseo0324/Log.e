package com.devhjs.loge.presentation.write

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.R
import com.devhjs.loge.presentation.component.AiAnalysisPlaceholder
import com.devhjs.loge.presentation.component.AiAnalysisResultCard
import com.devhjs.loge.presentation.component.AiAnalyzeButton
import com.devhjs.loge.presentation.component.CustomAppBar
import com.devhjs.loge.presentation.component.CustomButton
import com.devhjs.loge.presentation.component.WriteInputSection
import com.devhjs.loge.presentation.designsystem.AppColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WriteScreen(
    onBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showAiAnalysisResult by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomAppBar(
                title = "새 로그",
                titleIcon = R.drawable.ic_write_filled,
                onBackClick = onBackClick,
                actions = {
                    CustomButton(
                        modifier = Modifier
                            .width(68.dp)
                            .padding(end = 4.dp),
                        backgroundColor = AppColors.primary,
                        icon = R.drawable.ic_save,
                        text = "저장",
                        contentColor = AppColors.black,
                        onClick = onSaveClick
                    )
                }
            )
        },
        containerColor = AppColors.background,
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Title 입력
            WriteInputSection(
                label = "// 제목",
                placeholder = "오늘 무엇을 배웠나요?",
                minHeight = 44.dp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Learned
            WriteInputSection(
                label = "// 학습 내용",
                placeholder = "const learnings = [\n  'React hooks 사용법 학습',\n  'async/await 패턴 이해',\n  'CSS flexbox 레이아웃 마스터'\n];",
                minHeight = 120.dp,
                singleLine = false
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Difficult
            WriteInputSection(
                label = "// 어려웠던 점",
                placeholder = "오늘의 어려웠던 점을 입력",
                minHeight = 100.dp,
                singleLine = false
            )

            Spacer(modifier = Modifier.height(24.dp))

            // AI 분석 버튼
            AiAnalyzeButton(
                isLoading = isLoading,
                onClick = {
                    if (!isLoading && !showAiAnalysisResult) {
                        isLoading = true
                        coroutineScope.launch {
                            delay(3000) // todo: 추후 비즈니스 로직 연결 후 isLoading State 로 변경 예정
                            isLoading = false
                            showAiAnalysisResult = true
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (showAiAnalysisResult) {
                AiAnalysisResultCard()
                Spacer(modifier = Modifier.height(16.dp))
                AiAnalysisPlaceholder()
            } else {
                AiAnalysisPlaceholder()
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}



@Preview
@Composable
fun WriteScreenPreview() {
    WriteScreen()
}