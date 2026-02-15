package com.devhjs.loge.presentation.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.loge.R
import com.devhjs.loge.domain.model.FeedbackType
import com.devhjs.loge.presentation.component.FeedbackTypeButton
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@Composable
fun FeedbackScreen(
    state: FeedbackState,
    onAction: (FeedbackAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // 헤더
        Text(
            text = "피드백 보내기",
            style = AppTextStyles.JetBrain.Label.copy(
                color = AppColors.titleTextColor,
                fontSize = 18.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "앱에 대한 피드백을 보내주세요. 개선 사항이나 버그를\n알려주시면 감사하겠습니다.",
            style = AppTextStyles.JetBrain.Label.copy(
                color = AppColors.labelTextColor,
                fontSize = 14.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 피드백 유형
        Text(
            text = "피드백 유형",
            style = AppTextStyles.JetBrain.Label.copy(
                color = AppColors.contentTextColor,
                fontSize = 14.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FeedbackTypeButton(
                text = "버그",
                isSelected = state.type == FeedbackType.BUG,
                selectedColor = AppColors.red,
                onClick = { onAction(FeedbackAction.OnTypeSelected(FeedbackType.BUG)) },
                modifier = Modifier.weight(1f)
            )
            FeedbackTypeButton(
                text = "기능",
                isSelected = state.type == FeedbackType.FEATURE,
                selectedColor = AppColors.primary,
                onClick = { onAction(FeedbackAction.OnTypeSelected(FeedbackType.FEATURE)) },
                modifier = Modifier.weight(1f)
            )
            FeedbackTypeButton(
                text = "기타",
                isSelected = state.type == FeedbackType.OTHER,
                selectedColor = AppColors.blue,
                onClick = { onAction(FeedbackAction.OnTypeSelected(FeedbackType.OTHER)) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 제목
        Text(
            text = "제목",
            style = AppTextStyles.JetBrain.Label.copy(
                color = AppColors.contentTextColor,
                fontSize = 14.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.title,
            onValueChange = { onAction(FeedbackAction.OnTitleChanged(it)) },
            placeholder = {
                Text(
                    text = "피드백 제목을 입력하세요",
                    style = AppTextStyles.JetBrain.Label.copy(
                        color = AppColors.subTextColor,
                        fontSize = 16.sp
                    )
                )
            },
            textStyle = AppTextStyles.JetBrain.Label.copy(
                color = AppColors.titleTextColor,
                fontSize = 16.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = AppColors.cardInner,
                unfocusedContainerColor = AppColors.cardInner,
                focusedBorderColor = AppColors.border,
                unfocusedBorderColor = AppColors.border,
                cursorColor = AppColors.primary
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 내용
        Text(
            text = "내용",
            style = AppTextStyles.JetBrain.Label.copy(
                color = AppColors.contentTextColor,
                fontSize = 14.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.content,
            onValueChange = { onAction(FeedbackAction.OnContentChanged(it)) },
            placeholder = {
                Text(
                    text = "피드백 내용을 자세히 작성해주세요",
                    style = AppTextStyles.JetBrain.Label.copy(
                        color = AppColors.subTextColor,
                        fontSize = 16.sp
                    )
                )
            },
            textStyle = AppTextStyles.JetBrain.Label.copy(
                color = AppColors.titleTextColor,
                fontSize = 16.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = AppColors.cardInner,
                unfocusedContainerColor = AppColors.cardInner,
                focusedBorderColor = AppColors.border,
                unfocusedBorderColor = AppColors.border,
                cursorColor = AppColors.primary
            ),
            shape = RoundedCornerShape(8.dp),
            minLines = 5,
            maxLines = 8,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 보내기 버튼
        Button(
            onClick = { onAction(FeedbackAction.OnSubmit) },
            enabled = !state.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.primary,
                contentColor = Color.White,
                disabledContainerColor = AppColors.primary.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.White
                )
                Text(
                    text = "  보내기",
                    style = AppTextStyles.JetBrain.Label.copy(
                        color = Color.White,
                        fontSize = 14.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 취소 버튼
        Button(
            onClick = { onAction(FeedbackAction.OnCancel) },
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.cardInner,
                contentColor = AppColors.contentTextColor
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.border)
        ) {
            Text(
                text = "취소",
                style = AppTextStyles.JetBrain.Label.copy(
                    color = AppColors.contentTextColor,
                    fontSize = 14.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}



@Preview
@Composable
fun FeedbackScreenPreview() {
    FeedbackScreen(
        state = FeedbackState(),
        onAction = {}
    )
}
