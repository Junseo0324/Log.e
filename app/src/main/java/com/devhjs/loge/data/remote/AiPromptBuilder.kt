package com.devhjs.loge.data.remote

import javax.inject.Inject
import javax.inject.Singleton

/**
 * AI 요청에 사용할 프롬프트 문자열 생성 전담 클래스.
 * 어떤 내용을 AI에게 물어볼지 결정하는 것은 도메인 규칙이므로 Repository 밖으로 분리.
 */
@Singleton
class AiPromptBuilder @Inject constructor() {

    /**
     * 월간 회고 분석 프롬프트 생성.
     * @param date 분석 대상 월 ("yyyy-MM")
     * @param emotions 해당 월의 감정 레이블 목록
     * @param scores 감정 점수 목록
     * @param difficulties 난이도 목록
     */
    fun buildMonthlyFeedbackPrompt(
        date: String,
        emotions: List<String>,
        scores: List<Int>,
        difficulties: List<Int>
    ): String = """
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

    /**
     * 일일 TIL 분석 프롬프트 생성.
     * @param title TIL 제목
     * @param learned 학습 내용
     * @param difficult 어려운 점
     */
    fun buildDailyLogPrompt(
        title: String,
        learned: String,
        difficult: String
    ): String = """
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
}
