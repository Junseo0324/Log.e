package com.devhjs.loge.domain.repository

interface FileRepository {
    suspend fun saveToUri(uri: String, content: String)
}
