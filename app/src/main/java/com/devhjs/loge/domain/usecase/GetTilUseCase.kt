package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTilUseCase @Inject constructor(
    private val repository: TilRepository,
) {
    operator fun invoke(id: Long): Flow<Til> {
        return repository.getTil(id)
    }
}
