package com.devhjs.loge.data.repository

import com.devhjs.loge.core.util.Result
import com.devhjs.loge.data.dto.ApiMessage
import com.devhjs.loge.data.dto.OpenAiRequest
import com.devhjs.loge.data.local.dao.MonthlyReviewDao
import com.devhjs.loge.data.local.entity.MonthlyReviewEntity
import com.devhjs.loge.data.remote.OpenAiService
import com.devhjs.loge.data.remote.dto.toRemoteDto
import com.devhjs.loge.domain.model.AiReport
import com.devhjs.loge.domain.repository.AiRepository
import com.devhjs.loge.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val openAiService: OpenAiService,
    private val json: Json,
    private val monthlyReviewDao: MonthlyReviewDao,
    private val supabaseClient: SupabaseClient,
    private val authRepository: AuthRepository
) : AiRepository {

    override suspend fun getAiFeedback(
        date: String,
        emotions: List<String>,
        scores: List<Int>,
        difficulties: List<Int>
    ): Result<AiReport, Exception> {
        val prompt = """
            $date 월의 다음 사용자 데이터를 분석하고 JSON 형식으로 보고서를 제공하세요.
            
            데이터:
            - 감정: $emotions
            - 점수: $scores
            - 난이도: $difficulties
            
            다음 필드를 포함하는 JSON 객체만 반환하세요:
            - emotion: 전체적인 감정 요약 (String, 다음 중 하나: "성취감", "만족", "평범", "어려움", "좌절")
            - emotionScore: 평균 점수 (Int, 1-5)
            - difficultyLevel: 전체적인 난이도 요약 (String, 다음 중 하나: "쉬움", "보통", "어려움", "매우 어려움")
            - comment: 월간 학습 흐름, 감정 변화, 성장을 종합적으로 분석한 상세한 피드백 (String, 3문단 내외, 한국어). 어떤 점이 좋았고 어떤 점을 보완하면 좋을지 구체적으로 작성해주세요.
            
            중요: JSON의 모든 문자열 값은 한국어여야 합니다.
            
            예시 JSON:
            {
                "emotion": "만족",
                "emotionScore": 4,
                "difficultyLevel": "보통",
                "comment": "이번 달은 전반적으로 꾸준한 학습 태도가 돋보였습니다. 특히 초반의 어려움을 딛고 후반으로 갈수록 성취감을 느끼는 모습이 인상적입니다.\n\n감정 점수도 안정적인 상승 곡선을 그리고 있어 긍정적입니다. 다만, 난이도가 높은 과제에서는 다소 좌절하는 경향이 있으니 작은 목표부터 성취해나가는 전략을 추천합니다.\n\n다음 달에는 새로운 기술 스택에 도전하며 학습의 폭을 넓혀보는 것도 좋겠습니다. 지금처럼 꾸준히 정진한다면 분명 좋은 결과가 있을 것입니다."
            }
        """.trimIndent()

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
        val prompt = """
            다음은 사용자가 작성한 오늘 배운 내용(TIL)입니다. 이 내용을 분석하고 다음 항목을 JSON 형식으로 제공해주세요.
            
            제목: $title
            학습 내용: $learned
            어려웠던 점: $difficult
            
            분석 항목:
            1. emotion: 전체적인 감정 (String, 다음 중 하나: "성취감", "만족", "평범", "어려움", "좌절")
            2. emotionScore: 감정 점수 (Int, 0-100)
               - 성취감: 90-100
               - 만족: 70-89
               - 평범: 50-69
               - 어려움: 30-49
               - 좌절: 0-29
            3. difficultyLevel: 난이도 (String, 다음 중 하나: "쉬움", "보통", "어려움", "매우 어려움") -> 1(쉬움)~4(매우 어려움)으로 매핑될 예정이므로 텍스트로 주세요.
               (앱에서 매핑: 쉬움=1, 보통=2, 어려움=3, 매우 어려움=4)
            4. comment: 감정 분석과 기술적인 내용에 대한 피드백을 포함한 코멘트 (String, 5줄 내외, 친근한 말투). 기술적인 내용이 있다면 그에 대한 칭찬이나 조언도 포함해주세요.

            JSON 예시:
            {
                "emotion": "만족",
                "emotionScore": 85,
                "difficultyLevel": "보통",
                "comment": "오늘도 수고 많으셨어요! 꾸준함이 돋보입니다."
            }
        """.trimIndent()

        return getAiResponse(
            systemRole = "당신은 개발자의 감정과 성장을 분석하는 전문가입니다. 오직 JSON 형식으로만 응답하세요.",
            prompt = prompt
        )
    }

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

            // 2. Remote DB 동기화 (로그인 상태인 경우)
            if (authRepository.isUserLoggedIn()) {
                val currentUserId = authRepository.getCurrentUserUid()
                if (currentUserId == userId) {
                    val dto = entity.toRemoteDto() // DTO 변환
                    supabaseClient.from("monthly_reviews").upsert(dto) {
                        onConflict = "user_id, year_month"
                    }
                    Timber.d("Remote MonthlyReview Saved: $yearMonth")
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Failed to save monthly review")
            Result.Error(e)
        }
    }

    override suspend fun getSavedMonthlyReview(yearMonth: String): Result<AiReport?, Exception> = withContext(Dispatchers.IO) {
        try {
            // Local DB 조회
            val userId = authRepository.getCurrentUserUid()
            if (userId != null) {
                // 로그인된 경우 해당 유저의 데이터 조회
                val entity = monthlyReviewDao.getMonthlyReviewSync(userId, yearMonth)
                if (entity != null) {
                    Timber.d("Found local monthly review for $yearMonth")
                    return@withContext Result.Success(entity.toDomain())
                }
            }

            Result.Success(null)
        } catch (e: Exception) {
            Timber.e(e, "Failed to get saved monthly review")
            Result.Error(e)
        }
    }

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
            val content = response.choices.firstOrNull()?.message?.content ?: throw Exception("No response from AI")

            val jsonString = content.substring(
                content.indexOf("{"),
                content.lastIndexOf("}") + 1
            )

            val parsedResponse = json.decodeFromString<AiResponseJson>(jsonString)

            val aiReport = AiReport(
                date = System.currentTimeMillis(),
                emotion = parsedResponse.emotion,
                emotionScore = parsedResponse.emotionScore,
                difficultyLevel = parsedResponse.difficultyLevel,
                comment = parsedResponse.comment
            )

            Result.Success(aiReport)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    @Serializable
    private data class AiResponseJson(
        val emotion: String,
        val emotionScore: Int,
        val difficultyLevel: String,
        val comment: String
    )

    @Serializable
    private data class DailyUsageInsert(
        val user_id: String,
        val used_date: String
    )

    // 오늘 날짜(today: "yyyy-MM-dd")로 Supabase에 사용 기록이 있는지 조회
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
                    .decodeList<Map<String, kotlinx.serialization.json.JsonElement>>()
                Result.Success(rows.isNotEmpty())
            } catch (e: Exception) {
                Timber.e(e, "checkDailyAnalysisUsed 실패")
                Result.Error(e)
            }
        }

    // 오늘 날짜(today: "yyyy-MM-dd")로 Supabase에 사용 기록 저장
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
}
