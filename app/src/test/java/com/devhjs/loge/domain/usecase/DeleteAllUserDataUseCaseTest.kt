package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.TilRepository
import com.devhjs.loge.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.Exception

class DeleteAllUserDataUseCaseTest {

    private val tilRepository: TilRepository = mockk(relaxed = true)
    private val userRepository: UserRepository = mockk(relaxed = true)
    private val useCase = DeleteAllUserDataUseCase(tilRepository, userRepository)

    @Test
    fun `모든 데이터 삭제가 성공하면 Success를 반환하는지 확인`() = runTest {
        // Given
        coEvery { tilRepository.deleteAll() } returns Unit
        coEvery { userRepository.deleteAll() } returns Unit

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Success)
        coVerify { tilRepository.deleteAll() }
        coVerify { userRepository.deleteAll() }
    }

    @Test
    fun `레포지토리에서 에러 발생 시 Error를 반환하는지 확인`() = runTest {
        // Given
        val exception = Exception("Delete failed")
        coEvery { tilRepository.deleteAll() } throws exception

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Error)
        coVerify { tilRepository.deleteAll() }
    }
}
