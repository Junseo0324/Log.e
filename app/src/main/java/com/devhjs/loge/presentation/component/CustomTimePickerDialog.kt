package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.devhjs.loge.R
import com.devhjs.loge.presentation.designsystem.LogETheme
import com.devhjs.loge.presentation.designsystem.AppTextStyles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    onDismissRequest: () -> Unit,
    onTimeSelected: (Int, Int) -> Unit
) {
    var selectedHour by remember { mutableIntStateOf(initialHour) }
    var selectedMinute by remember { mutableIntStateOf(initialMinute) }

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .background(LogETheme.colors.background, RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "알림 시간 설정",
                style = AppTextStyles.JetBrain.Label.copy(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "매일 알림을 받을 시간을 선택해주세요",
                style = AppTextStyles.JetBrain.Label.copy(
                    color = LogETheme.colors.subTextColor,
                    fontSize = 14.sp
                )
            )
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF13231C), RoundedCornerShape(12.dp))
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time), // Ensure this icon exists
                        contentDescription = null,
                        tint = LogETheme.colors.iconPrimary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "%02d:%02d".format(selectedHour, selectedMinute),
                        style = AppTextStyles.JetBrain.Header4.copy(
                            color = Color.White,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "시",
                        style = AppTextStyles.JetBrain.Label.copy(
                            color = LogETheme.colors.subTextColor,
                            fontSize = 12.sp
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    TimeDropdown(
                        items = (0..23).toList(),
                        selectedItem = selectedHour,
                        onItemSelected = { selectedHour = it },
                        format = { "%02d시".format(it) }
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "분",
                        style = AppTextStyles.JetBrain.Label.copy(
                            color = LogETheme.colors.subTextColor,
                            fontSize = 12.sp
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    TimeDropdown(
                        items = (0..59).toList(),
                        selectedItem = selectedMinute,
                        onItemSelected = { selectedMinute = it },
                        format = { "%02d분".format(it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Buttons
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LogETheme.colors.primary)
                    .clickable { onTimeSelected(selectedHour, selectedMinute) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "완료",
                    style = AppTextStyles.JetBrain.Label.copy(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LogETheme.colors.cardInner)
                    .clickable { onDismissRequest() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "취소",
                    style = AppTextStyles.JetBrain.Label.copy(
                        color = LogETheme.colors.white,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
