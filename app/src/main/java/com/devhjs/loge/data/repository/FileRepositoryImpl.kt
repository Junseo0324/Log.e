package com.devhjs.loge.data.repository

import android.content.Context
import androidx.core.net.toUri
import com.devhjs.loge.domain.repository.FileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FileRepository {

    override suspend fun saveToUri(uri: String, content: String) {
        withContext(Dispatchers.IO) {
            val parsedUri = uri.toUri()
            val outputStream = context.contentResolver.openOutputStream(parsedUri)
                ?: throw Exception("Cannot open output stream")

            OutputStreamWriter(outputStream).use { writer ->
                writer.write(content)
            }
        }
    }
}
