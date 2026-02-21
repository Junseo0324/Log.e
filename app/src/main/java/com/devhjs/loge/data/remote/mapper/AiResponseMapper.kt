package com.devhjs.loge.data.remote.mapper

import com.devhjs.loge.domain.model.AiReport
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

/**
 * OpenAI 응답 문자열을 파싱해 [AiReport] 도메인 모델로 변환하는 Mapper.
 * JSON 파싱·변환 책임을 Repository에서 분리.
 */
@Singleton
class AiResponseMapper @Inject constructor(
    private val json: Json
) {
    /**
     * AI 응답 raw 문자열에서 JSON{ } 블록을 추출하고 [AiReport]로 변환.
     * @throws Exception JSON 추출 또는 파싱 실패 시
     */
    fun parse(rawContent: String): AiReport {
        val jsonString = rawContent.substring(
            rawContent.indexOf("{"),
            rawContent.lastIndexOf("}") + 1
        )
        val parsed = json.decodeFromString<AiResponseJson>(jsonString)
        return AiReport(
            date = System.currentTimeMillis(),
            emotion = parsed.emotion,
            emotionScore = parsed.emotionScore,
            difficultyLevel = parsed.difficultyLevel,
            comment = parsed.comment
        )
    }

    /** AI가 반환하는 JSON 구조 */
    @Serializable
    private data class AiResponseJson(
        val emotion: String,
        val emotionScore: Int,
        val difficultyLevel: String,
        val comment: String
    )
}
