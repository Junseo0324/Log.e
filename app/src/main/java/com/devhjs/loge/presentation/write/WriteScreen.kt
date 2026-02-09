package com.devhjs.loge.presentation.write

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.loge.presentation.component.AiAnalysisPlaceholder
import com.devhjs.loge.presentation.component.AiAnalysisResultCard
import com.devhjs.loge.presentation.component.AiAnalyzeButton
import com.devhjs.loge.presentation.component.WriteInputSection


@Composable
fun WriteScreen(
    state: WriteState,
    onAction: (WriteAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Title 입력
        WriteInputSection(
            label = "// 제목",
            placeholder = "오늘 무엇을 배웠나요?",
            value = state.title,
            onValueChange = { onAction(WriteAction.OnTitleChange(it)) },
            minHeight = 44.dp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Learned
        WriteInputSection(
            label = "// 학습 내용",
            placeholder = "const learnings = [\n  'React hooks 사용법 학습',\n  'async/await 패턴 이해',\n  'CSS flexbox 레이아웃 마스터'\n];",
            value = state.learnings,
            onValueChange = { onAction(WriteAction.OnLearningsChange(it)) },
            minHeight = 120.dp,
            singleLine = false
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Difficult
        WriteInputSection(
            label = "// 어려웠던 점",
            placeholder = "오늘의 어려웠던 점을 입력",
            value = state.difficulties,
            onValueChange = { onAction(WriteAction.OnDifficultiesChange(it)) },
            minHeight = 100.dp,
            singleLine = false
        )

        Spacer(modifier = Modifier.height(24.dp))

        // AI 분석 버튼
        AiAnalyzeButton(
            isLoading = state.isLoading,
            onClick = { onAction(WriteAction.OnAiAnalyzeClick) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.showAiAnalysisResult) {
            AiAnalysisResultCard()
            Spacer(modifier = Modifier.height(16.dp))
            AiAnalysisPlaceholder()
        } else {
            AiAnalysisPlaceholder()
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview
@Composable
fun WriteScreenPreview() {
    WriteScreen(
        state = WriteState(),
        onAction = {}
    )
}