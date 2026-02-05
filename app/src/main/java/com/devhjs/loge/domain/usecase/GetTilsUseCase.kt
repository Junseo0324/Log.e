package com.devhjs.loge.domain.usecase

import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTilsUseCase @Inject constructor(
    private val repository: TilRepository,
) {
    operator fun invoke(start: Long, end: Long): Flow<List<Til>> {
        return repository.getAllTil(start, end)
    }
}
