package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.User
import com.devhjs.loge.domain.repository.NotificationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateNotificationSettingUseCaseTest {

    private lateinit var saveUserUseCase: SaveUserUseCase
    private lateinit var notificationRepository: NotificationRepository
    private lateinit var updateNotificationSettingUseCase: UpdateNotificationSettingUseCase

    @Before
    fun setUp() {
        saveUserUseCase = mockk(relaxed = true)
        notificationRepository = mockk(relaxed = true)
        updateNotificationSettingUseCase = UpdateNotificationSettingUseCase(
            saveUserUseCase,
            notificationRepository
        )
    }

    @Test
    fun `알림 설정이 켜져 있으면 해당 시간으로 알림을 예약하고 유저 정보를 저장해야 한다`() = runBlocking {
        // Given
        val customHour = 8
        val customMinute = 30
        val user = User(
            id = 1L,
            name = "Test",
            githubId = "id",
            isNotificationEnabled = true,
            isDarkModeEnabled = false,
            notificationTime = Pair(customHour, customMinute)
        )
        coEvery { saveUserUseCase(user) } returns Result.Success(Unit)

        // When
        updateNotificationSettingUseCase(user)

        // Then
        coVerify(exactly = 1) { saveUserUseCase(user) }
        coVerify(exactly = 1) { notificationRepository.scheduleReminder(customHour, customMinute) }
        coVerify(exactly = 0) { notificationRepository.cancelReminder() }
    }

    @Test
    fun `알림 설정이 꺼져 있으면 알림 예약을 취소하고 유저 정보를 저장해야 한다`() = runBlocking {
        // Given
        val user = User(
            id = 1L,
            name = "Test",
            githubId = "id",
            isNotificationEnabled = false,
            isDarkModeEnabled = false,
            notificationTime = Pair(21, 0)
        )
        coEvery { saveUserUseCase(user) } returns Result.Success(Unit)

        // When
        updateNotificationSettingUseCase(user)

        // Then
        coVerify(exactly = 1) { saveUserUseCase(user) }
        coVerify(exactly = 0) { notificationRepository.scheduleReminder(any(), any()) }
        coVerify(exactly = 1) { notificationRepository.cancelReminder() }
    }
}
