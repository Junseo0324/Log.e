package com.devhjs.loge.data.repository

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.data.dto.ApiMessage
import com.devhjs.loge.data.dto.OpenAiRequest
import com.devhjs.loge.data.local.dao.MonthlyReviewDao
import com.devhjs.loge.data.local.entity.MonthlyReviewEntity
import com.devhjs.loge.data.remote.AiPromptBuilder
import com.devhjs.loge.data.remote.OpenAiService
import com.devhjs.loge.data.mapper.toRemoteDto
import com.devhjs.loge.data.remote.mapper.AiResponseMapper
import com.devhjs.loge.domain.model.AiReport
import com.devhjs.loge.domain.repository.AiRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import timber.log.Timber
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val openAiService: OpenAiService,
    private val promptBuilder: AiPromptBuilder,
    private val responseMapper: AiResponseMapper,
    private val monthlyReviewDao: MonthlyReviewDao,
    private val supabaseClient: SupabaseClient
) : AiRepository {

    // ──────────────────────────────────────────────
    // AI 분석 요청
    // ──────────────────────────────────────────────

    override suspend fun getAiFeedback(
        date: String,
        emotions: List<String>,
        scores: List<Int>,
        difficulties: List<Int>
    ): Result<AiReport, Exception> {
        val prompt = promptBuilder.buildMonthlyFeedbackPrompt(date, emotions, scores, difficulties)
        return getAiResponse(
            systemRole = "당신은 사용자 로그를 분석하고 JSON 형식으로 피드백을 제공하는 유용한 어시스턴트입니다.",
            prompt = prompt
        )
    }

    override suspend fun analyzeLog(
        title: String,
        learned: String,
        difficult: String
    ): Result<AiReport, Exception> {
        val prompt = promptBuilder.buildDailyLogPrompt(title, learned, difficult)
        return getAiResponse(
            systemRole = "당신은 개발자의 감정과 성장을 분석하는 전문가입니다. 오직 JSON 형식으로만 응답하세요.",
            prompt = prompt
        )
    }

    // ──────────────────────────────────────────────
    // 월간 리뷰 저장/조회
    // ──────────────────────────────────────────────

    /**
     * 월간 리뷰를 Local DB와 Remote DB에 저장.
     * 로그인 여부 / userId 검증은 호출하는 UseCase(GetMonthlyReviewUseCase)에서 담당.
     * Repository는 받은 userId로 저장만 수행.
     */
    override suspend fun saveMonthlyReview(
        userId: String,
        yearMonth: String,
        report: AiReport
    ): Result<Unit, Exception> = withContext(Dispatchers.IO) {
        try {
            // 1. Local DB 저장
            val entity = MonthlyReviewEntity(
                userId = userId,
                yearMonth = yearMonth,
                emotion = report.emotion,
                emotionScore = report.emotionScore,
                difficultyLevel = report.difficultyLevel,
                comment = report.comment,
                createdAt = report.date
            )
            monthlyReviewDao.insertMonthlyReview(entity)
            Timber.d("Local MonthlyReview Saved: $yearMonth")

            // 2. Remote DB 저장 (userId가 있는 경우에만 호출됨 — UseCase에서 보장)
            val dto = entity.toRemoteDto()
            supabaseClient.from("monthly_reviews").upsert(dto) {
                onConflict = "user_id, year_month"
            }
            Timber.d("Remote MonthlyReview Saved: $yearMonth")

            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Failed to save monthly review")
            Result.Error(e)
        }
    }

    override suspend fun getSavedMonthlyReview(userId: String, yearMonth: String): Result<AiReport?, Exception> =
        withContext(Dispatchers.IO) {
            try {
                val entity = monthlyReviewDao.getMonthlyReviewSync(userId, yearMonth)
                if (entity != null) {
                    Timber.d("Found local monthly review for $yearMonth")
                    Result.Success(entity.toDomain())
                } else {
                    Result.Success(null)
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to get saved monthly review")
                Result.Error(e)
            }
        }

    // ──────────────────────────────────────────────
    // 일일 분석 사용 기록
    // ──────────────────────────────────────────────

    override suspend fun checkDailyAnalysisUsed(userId: String, today: String): Result<Boolean, Exception> =
        withContext(Dispatchers.IO) {
            try {
                val rows = supabaseClient.from("ai_daily_usage")
                    .select {
                        filter {
                            eq("user_id", userId)
                            eq("used_date", today)
                        }
                    }
                    .decodeList<Map<String, JsonElement>>()
                Result.Success(rows.isNotEmpty())
            } catch (e: Exception) {
                Timber.e(e, "checkDailyAnalysisUsed 실패")
                Result.Error(e)
            }
        }

    override suspend fun markDailyAnalysisUsed(userId: String, today: String): Result<Unit, Exception> =
        withContext(Dispatchers.IO) {
            try {
                supabaseClient.from("ai_daily_usage")
                    .insert(DailyUsageInsert(user_id = userId, used_date = today))
                Result.Success(Unit)
            } catch (e: Exception) {
                Timber.e(e, "markDailyAnalysisUsed 실패")
                Result.Error(e)
            }
        }

    // ──────────────────────────────────────────────
    // Private
    // ──────────────────────────────────────────────

    /** OpenAI API 호출 후 [AiResponseMapper]로 파싱해 [AiReport] 반환 */
    private suspend fun getAiResponse(
        systemRole: String,
        prompt: String
    ): Result<AiReport, Exception> = withContext(Dispatchers.IO) {
        try {
            val request = OpenAiRequest(
                messages = listOf(
                    ApiMessage(role = "system", content = systemRole),
                    ApiMessage(role = "user", content = prompt)
                )
            )
            val response = openAiService.getCompletion(request)
            val content = response.choices.firstOrNull()?.message?.content
                ?: throw Exception("No response from AI")

            // 파싱·변환을 AiResponseMapper에 완전히 위임
            val aiReport = responseMapper.parse(content)
            Result.Success(aiReport)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    @Serializable
    private data class DailyUsageInsert(
        val user_id: String,
        val used_date: String
    )
}
