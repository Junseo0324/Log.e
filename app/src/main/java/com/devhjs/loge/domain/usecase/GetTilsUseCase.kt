package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.TilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTilsUseCase @Inject constructor(
    private val repository: TilRepository,
) {
    operator fun invoke(start: Long, end: Long): Flow<Result<List<Til>, Throwable>> {
        return repository.getAllTil(start, end)
            .map { list ->
                Result.Success(list) as Result<List<Til>, Throwable>
            }
            .catch { e ->
                emit(Result.Error(e))
            }
    }
}
