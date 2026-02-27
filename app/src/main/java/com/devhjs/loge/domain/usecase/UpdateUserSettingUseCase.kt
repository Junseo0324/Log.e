package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.model.User
import com.devhjs.loge.domain.repository.NotificationRepository
import javax.inject.Inject

class UpdateUserSettingUseCase @Inject constructor(
    private val saveUserUseCase: SaveUserUseCase,
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(user: User) {
        // 사용자 정보 저장
        saveUserUseCase(user)

        // 알림 설정에 따른 처리
        if (user.isNotificationEnabled) {
            val time = user.notificationTime ?: Pair(21, 0)
            notificationRepository.scheduleReminder(time.first, time.second)
        } else {
            notificationRepository.cancelReminder()
        }
    }
}
