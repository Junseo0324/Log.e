package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.TilRepository
import javax.inject.Inject

class SyncTilsUseCase @Inject constructor(
    private val repository: TilRepository
) {
    suspend operator fun invoke(): Result<Unit, Exception> {
        return try {
            // 1. 원격 데이터를 로컬로 가져오기 (원격 -> 로컬)
            repository.fetchRemoteTilsToLocal()
            
            // 2. 로컬 데이터를 원격으로 동기화 (로컬 -> 원격)
            repository.syncAllTilsToRemote()
            
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
