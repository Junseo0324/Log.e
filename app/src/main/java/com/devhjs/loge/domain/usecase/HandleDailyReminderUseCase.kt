package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.NotificationRepository
import com.devhjs.loge.domain.service.NotificationService
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class HandleDailyReminderUseCase @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val notificationService: NotificationService,
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke() {
        // 사용자 정보 가져오기
        val userResult = getUserUseCase().firstOrNull() ?: return
        
        // Result 타입 처리
        val user = when (userResult) {
            is Result.Success -> userResult.data
            is Result.Error -> return
        }

        // 알림 설정 확인
        if (user.isNotificationEnabled) {
            // 알림 표시
            notificationService.showDailyReminder()

            // 다음 알림 예약 (사용자가 설정한 시간으로)
            val time = user.notificationTime
            if (time != null) {
                notificationRepository.scheduleReminder(time.first, time.second)
            } else {
                // 기본값 21:00
                notificationRepository.scheduleReminder(21, 0)
            }
        }
    }
}
