package com.devhjs.loge.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "til")
data class TilEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val createdAt: Long,
    val title: String,
    val learned: String,
    val difficult: String,
    val emotionScore: Int,
    val emotion: String,
    val difficultyLevel: Int,
    val updatedAt: Long,
    val aiFeedBack: String? = null
)
