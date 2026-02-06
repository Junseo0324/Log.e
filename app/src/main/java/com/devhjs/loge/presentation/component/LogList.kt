package com.devhjs.loge.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.domain.model.Til

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

            // 2. 로그 아이템 카드
            LogItemCard(
                item = til,
                onClick = { onLogClick(til.id) }
            )
        }
    }
}