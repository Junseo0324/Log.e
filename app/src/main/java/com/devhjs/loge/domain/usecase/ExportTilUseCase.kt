package com.devhjs.loge.domain.usecase

import com.devhjs.loge.core.util.DateUtils
import com.devhjs.loge.core.util.Result
import com.devhjs.loge.domain.repository.FileRepository
import com.devhjs.loge.domain.repository.TilRepository
import javax.inject.Inject

class ExportTilUseCase @Inject constructor(
    private val tilRepository: TilRepository,
    private val fileRepository: FileRepository
) {
    suspend operator fun invoke(uri: String): Result<Unit, Exception> {
        try {
            val mils = tilRepository.getAllTils()
            if (mils.isEmpty()) {
                return Result.Error(Exception("내보낼 데이터가 없습니다."))
            }

            val header = "날짜,제목,감정,난이도,학습 내용,추가 학습,시간\n"
            val content = StringBuilder(header)

            mils.forEach { til ->
                val date = DateUtils.formatToIsoDate(til.createdAt)
                val time = DateUtils.formatToTime(til.createdAt)

                val title = escapeCsv(til.title)
                val emotion = escapeCsv(til.emotion.toString()) 
                val difficulty = escapeCsv(til.difficultyLevel.toString())

                val learned = escapeCsv(til.learned)
                val extra = escapeCsv(til.difficult)
                
                content.append("$date,$title,$emotion,$difficulty,$learned,$extra,$time\n")
            }

            fileRepository.saveToUri(uri, content.toString())
            return Result.Success(Unit)

        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    private fun escapeCsv(value: String): String {
        var result = value
        if (result.contains(",") || result.contains("\"") || result.contains("\n")) {
            result = result.replace("\"", "\"\"")
            result = "\"$result\""
        }
        return result
    }
}
