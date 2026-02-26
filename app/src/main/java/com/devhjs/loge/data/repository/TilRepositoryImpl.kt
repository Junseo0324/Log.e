package com.devhjs.loge.data.repository
 
import com.devhjs.loge.data.dto.TilRemoteDto

import com.devhjs.loge.data.local.dao.TilDao
import com.devhjs.loge.data.local.entity.TilEntity
import com.devhjs.loge.data.mapper.toDomain
import com.devhjs.loge.data.mapper.toEntity
import com.devhjs.loge.data.mapper.toRemoteDto
import com.devhjs.loge.domain.model.ChartPoint
import com.devhjs.loge.domain.model.Stat
import com.devhjs.loge.domain.model.Til
import com.devhjs.loge.domain.repository.AuthRepository
import com.devhjs.loge.domain.repository.TilRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import com.devhjs.loge.core.util.DateUtils
import timber.log.Timber
import javax.inject.Inject
import kotlinx.coroutines.flow.map as flowMap

class TilRepositoryImpl @Inject constructor(
    private val tilDao: TilDao,
    private val supabaseClient: SupabaseClient,
    private val authRepository: AuthRepository
) : TilRepository {

    override fun getAllTil(start: Long, end: Long): Flow<List<Til>> {
        return tilDao.getTilsBetween(start, end).flowMap { entities: List<TilEntity> ->
            entities.map { entity -> entity.toDomain() }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAllTils(): List<Til> {
        return withContext(Dispatchers.IO) {
            tilDao.getAllTils().map { it.toDomain() }
        }
    }

    override fun getTil(id: Long): Flow<Til> {
        return tilDao.getTilById(id).mapNotNull { it.firstOrNull()?.toDomain() }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveTil(til: Til) {
        withContext(Dispatchers.IO) {
            // 로컬 DB에 저장
            tilDao.insertTil(til.toEntity())

            // 로그인 상태라면 원격 DB에도 동기화
            syncToRemoteIfLoggedIn(til)
        }
    }

    override suspend fun updateTil(til: Til) {
        withContext(Dispatchers.IO) {
            // 로컬 DB 업데이트
            tilDao.updateTil(til.toEntity())

            // 로그인 상태라면 원격 DB에도 동기화
            syncToRemoteIfLoggedIn(til)
        }
    }

    override suspend fun deleteTil(til: Til) {
        withContext(Dispatchers.IO) {
            // 로컬 DB에서 삭제
            tilDao.deleteTil(til.toEntity())

            // 로그인 상태라면 원격 DB에서도 삭제
            deleteFromRemoteIfLoggedIn(til.id)
        }
    }

    override suspend fun deleteTil(id: Long) {
        withContext(Dispatchers.IO) {
            // 로컬 DB에서 삭제
            tilDao.deleteTilById(id)

            // 로그인 상태라면 원격 DB에서도 삭제
            deleteFromRemoteIfLoggedIn(id)
        }
    }

    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            // 로컬 DB 전체 삭제
            tilDao.deleteAll()

            // 로그인 상태라면 원격 데이터도 전체 삭제
            if (authRepository.isUserLoggedIn()) {
                val userId = authRepository.getCurrentUserUid()
                if (userId != null) {
                    try {
                        supabaseClient.from("tils").delete {
                            filter { eq("user_id", userId) }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    override fun getMonthlyStats(month: String): Flow<Stat> {
        val (startOfMonth, endOfMonth) = DateUtils.getMonthStartEndTimestamps(month)

        return tilDao.getTilsBetween(startOfMonth, endOfMonth).flowMap { entities: List<TilEntity> ->
            val tils: List<Til> = entities.map { entity -> entity.toDomain() }
            
            if (tils.isEmpty()) {
                return@flowMap Stat(
                    date = month,
                    totalTil = 0,
                    avgEmotion = 0f,
                    avgScore = 0f,
                    avgDifficulty = 0f,
                    emotionScoreList = emptyList(),
                    learnedDates = emptyList()
                )
            }

            val totalTil = tils.size
            val avgEmotion = tils.map { it.emotionScore }.average().toFloat() 
            val avgScore = tils.map { it.emotionScore }.average().toFloat()
            val avgDifficulty = tils.map { it.difficultyLevel }.average().toFloat()
            
            val emotionScoreList = tils.map { til: Til ->
                ChartPoint(
                    x = DateUtils.getDayOfMonth(til.createdAt).toFloat(), 
                    y = til.emotionScore.toFloat()
                )
            }.sortedBy { it.x }

            val learnedDates = tils.map { til: Til ->
                DateUtils.formatToIsoDate(til.createdAt)
            }.distinct()

            Stat(
                date = month,
                totalTil = totalTil,
                avgEmotion = avgEmotion,
                avgScore = avgScore,
                avgDifficulty = avgDifficulty,
                emotionScoreList = emotionScoreList,
                learnedDates = learnedDates,
                aiReport = null
            )
        }.flowOn(Dispatchers.IO)
    }

    /**
     * 로그인 후 기존 로컬 TIL 전체를 원격에 일괄 업로드
     */
    /**
     * 로그인 후 기존 로컬 TIL 전체를 원격에 일괄 업로드
     */
    override suspend fun syncAllTilsToRemote() {
        withContext(Dispatchers.IO) {
            if (!authRepository.isUserLoggedIn()) return@withContext

            val userId = authRepository.getCurrentUserUid() ?: return@withContext
            Timber.d("syncAllTilsToRemote 시작: userId=$userId")
            
            val allTils = tilDao.getAllTils().map { it.toDomain() }

            if (allTils.isEmpty()) {
                Timber.d("동기화할 로컬 TIL 데이터가 없습니다.")
                return@withContext
            }

            val dtos = allTils.map { it.toRemoteDto(userId) }
            // upsert로 중복 방지 (user_id + local_id 유니크 제약)
            try {
                supabaseClient.from("tils").upsert(dtos) {
                    onConflict = "user_id, local_id"
                }
                Timber.d("syncAllTilsToRemote 성공: ${dtos.size}개 동기화 완료")
            } catch (e: Exception) {
                Timber.e(e, "syncAllTilsToRemote 실패")
                throw e
            }
        }
    }

    /**
     * 원격(Supabase) 데이터를 로컬 DB로 가져옴
     */
    override suspend fun fetchRemoteTilsToLocal() {
        withContext(Dispatchers.IO) {
            if (!authRepository.isUserLoggedIn()) return@withContext

            val userId = authRepository.getCurrentUserUid() ?: return@withContext
            Timber.d("fetchRemoteTilsToLocal 시작: userId=$userId")

            try {
                val remoteTils = supabaseClient.from("tils")
                    .select {
                        filter { eq("user_id", userId) }
                    }
                    .decodeList<TilRemoteDto>()

                if (remoteTils.isNotEmpty()) {
                    val entities = remoteTils.map { it.toEntity() }
                    tilDao.insertTils(entities)
                    Timber.d("fetchRemoteTilsToLocal 성공: ${entities.size}개 가져옴")
                } else {
                    Timber.d("가져올 원격 데이터가 없습니다.")
                }
            } catch (e: Exception) {
                Timber.e(e, "fetchRemoteTilsToLocal 실패")
                throw e
            }
        }
    }

    /**
     * 로그인 상태일 때 단일 TIL을 원격에 동기화
     */
    private suspend fun syncToRemoteIfLoggedIn(til: Til) {
        if (authRepository.isUserLoggedIn()) {
            val userId = authRepository.getCurrentUserUid()
            if (userId != null) {
                try {
                    Timber.d("syncToRemoteIfLoggedIn 시도: tilId=${til.id}, title=${til.title}")
                    supabaseClient.from("tils").upsert(til.toRemoteDto(userId)) {
                        onConflict = "user_id, local_id"
                    }
                    Timber.d("syncToRemoteIfLoggedIn 성공")
                } catch (e: Exception) {
                    // 원격 동기화 실패 시 로컬 데이터는 유지
                    Timber.e(e, "syncToRemoteIfLoggedIn 실패: tilId=${til.id}")
                }
            } else {
                Timber.w("syncToRemoteIfLoggedIn 실패: UserId가 null입니다.")
            }
        }
    }

    /**
     * 로그인 상태일 때 원격에서 TIL 삭제
     */
    private suspend fun deleteFromRemoteIfLoggedIn(localId: Long) {
        if (authRepository.isUserLoggedIn()) {
            val userId = authRepository.getCurrentUserUid()
            if (userId != null) {
                try {
                    Timber.d("deleteFromRemoteIfLoggedIn 시도: localId=$localId")
                    supabaseClient.from("tils").delete {
                        filter {
                            eq("user_id", userId)
                            eq("local_id", localId)
                        }
                    }
                    Timber.d("deleteFromRemoteIfLoggedIn 성공")
                } catch (e: Exception) {
                    Timber.e(e, "deleteFromRemoteIfLoggedIn 실패: localId=$localId")
                }
            }
        }
    }
}

