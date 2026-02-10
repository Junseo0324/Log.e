package com.devhjs.loge.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devhjs.loge.R
import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.presentation.designsystem.AppColors

/**
 * 로그 리스트 컴포넌트
 * 1일 1로그 정책으로, 각 로그 상단에 날짜 섹션 헤더를 함께 표시함.
 * 리스트를 단순히 순회하며 헤더 + 카드를 그림.
 */
@Composable
fun LogList(
    logs: List<Til>,
    currentDate: String,
    onLogClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = logs,
            key = { it.id }
        ) { til ->
            val date = DateUtils.formatToDate(til.createdAt)
            val dayOfWeek = DateUtils.formatToDayOfWeek(til.createdAt)
            val isToday = date == currentDate
            
            // 1. 날짜 섹션 헤더 (각 로그마다 표시)
            DateSectionHeader(
                date = date,
                dayOfWeek = dayOfWeek,
                isToday = isToday
            )

            // 2. 로그 아이템 카드 (SwipeToDismiss 적용)
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { dismissValue ->
                    if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                        onDeleteClick(til.id)
                        false
                    } else {
                        false
                    }
                }
            )

            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    val color = if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                        AppColors.red
                    } else {
                        Color.Transparent
                    }
                    
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color)
                            .padding(end = 20.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                    }
                },
                content = {
                    LogItemCard(
                        item = til,
                        onClick = { onLogClick(til.id) }
                    )
                },
                enableDismissFromStartToEnd = false
            )
        }
    }
}