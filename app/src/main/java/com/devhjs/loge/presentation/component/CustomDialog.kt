package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.devhjs.loge.presentation.designsystem.AppColors
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    confirmText: String = "확인",
    dismissText: String = "취소",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier.width(320.dp),
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .background(AppColors.cardBackground, RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = title,
                    style = AppTextStyles.Pretendard.Header3.copy(color = AppColors.white)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = description,
                    style = AppTextStyles.Pretendard.Body.copy(color = AppColors.subTextColor)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = dismissText,
                            style = AppTextStyles.Pretendard.Body.copy(color = AppColors.labelTextColor)
                        )
                    }
                    TextButton(onClick = onConfirm) {
                        Text(
                            text = confirmText,
                            style = AppTextStyles.Pretendard.Body.copy(color = AppColors.red)
                        )
                    }
                }
            }
        }
    }
}
