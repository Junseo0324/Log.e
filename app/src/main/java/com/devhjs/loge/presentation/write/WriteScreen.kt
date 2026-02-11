package com.devhjs.loge.presentation.write

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
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
            .padding(16.dp)
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Focus Requester 설정
        val learnedFocusRequester = remember { FocusRequester() }
        val difficultFocusRequester = remember { FocusRequester() }

        // Title 입력
        WriteInputSection(
            label = "// 제목",
            placeholder = "오늘 무엇을 배웠나요?",
            value = state.title,
            onValueChange = { onAction(WriteAction.OnTitleChange(it)) },
            minHeight = 44.dp,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { learnedFocusRequester.requestFocus() }
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Learned
        WriteInputSection(
            modifier = Modifier.focusRequester(learnedFocusRequester),
            label = "// 학습 내용",
            placeholder = "오늘 배운 점을 입력하세요",
            value = state.learnings,
            onValueChange = { onAction(WriteAction.OnLearningsChange(it)) },
            minHeight = 120.dp,
            singleLine = false,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { difficultFocusRequester.requestFocus() }
            ),
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Difficult
        WriteInputSection(
            modifier = Modifier.focusRequester(difficultFocusRequester),
            label = "// 어려웠던 점",
            placeholder = "오늘의 어려웠던 점을 입력하세요",
            value = state.difficulties,
            onValueChange = { onAction(WriteAction.OnDifficultiesChange(it)) },
            minHeight = 100.dp,
            singleLine = false,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        )

        Spacer(modifier = Modifier.height(24.dp))

        // AI 분석 버튼
        AiAnalyzeButton(
            isLoading = state.isLoading,
            onClick = { onAction(WriteAction.OnAiAnalyzeClick) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.showAiAnalysisResult) {
            AiAnalysisResultCard(
                emotion = state.emotion,
                score = state.emotionScore,
                difficultyLevel = state.difficultyLevel,
                comment = state.aiFeedbackComment ?: "분석된 내용이 없습니다."
            )
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