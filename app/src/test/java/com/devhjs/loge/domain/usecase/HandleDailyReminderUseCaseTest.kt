package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.User
import com.devhjs.loge.domain.repository.NotificationRepository
import com.devhjs.loge.domain.service.NotificationService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HandleDailyReminderUseCaseTest {

    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var notificationService: NotificationService
    private lateinit var notificationRepository: NotificationRepository
    private lateinit var handleDailyReminderUseCase: HandleDailyReminderUseCase

    @Before
    fun setUp() {
        getUserUseCase = mockk()
        notificationService = mockk(relaxed = true)
        notificationRepository = mockk(relaxed = true)
        handleDailyReminderUseCase = HandleDailyReminderUseCase(
            getUserUseCase,
            notificationService,
            notificationRepository
        )
    }

    @Test
    fun `알림이 켜져 있으면 알림을 표시하고 해당 시간으로 예약해야 한다`() = runBlocking {
        // Given
        val customHour = 10
        val customMinute = 30
        val user = User(
            id = 1L,
            name = "Test User",
            githubId = "test_user",
            isNotificationEnabled = true,
            isDarkModeEnabled = false,
            notificationTime = Pair(customHour, customMinute)
        )
        
        every { getUserUseCase() } returns flowOf(Result.Success(user))

        // When
        handleDailyReminderUseCase()

        // Then
        verify { notificationService.showDailyReminder() }
        coVerify { notificationRepository.scheduleReminder(customHour, customMinute) }
    }

    @Test
    fun `알림이 꺼져 있으면 아무 동작도 하지 않아야 한다`() = runBlocking {
        // Given
        val user = User(
            id = 1L,
            name = "Test User",
            githubId = "test_user",
            isNotificationEnabled = false,
            isDarkModeEnabled = false,
            notificationTime = Pair(21, 0)
        )
        
        every { getUserUseCase() } returns flowOf(Result.Success(user))

        // When
        handleDailyReminderUseCase()

        // Then
        verify(exactly = 0) { notificationService.showDailyReminder() }
        coVerify(exactly = 0) { notificationRepository.scheduleReminder(any(), any()) }
    }

    @Test
    fun `사용자 정보를 가져오지 못하면 아무 동작도 하지 않아야 한다`() = runBlocking {
        // Given
        every { getUserUseCase() } returns flowOf(Result.Error(Exception("User not found")))

        // When
        handleDailyReminderUseCase()

        // Then
        verify(exactly = 0) { notificationService.showDailyReminder() }
        coVerify(exactly = 0) { notificationRepository.scheduleReminder(any(), any()) }
    }
}
