package com.devhjs.loge.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devhjs.loge.data.local.LogEDatabase
import com.devhjs.loge.data.local.entity.TilEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TilDaoTest {

    private lateinit var tilDao: TilDao
    private lateinit var db: LogEDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, LogEDatabase::class.java
        ).build()
        tilDao = db.tilDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTil() = runBlocking {
        val til = TilEntity(
            id = 1L,
            createdAt = System.currentTimeMillis(),
            title = "테스트 제목",
            learned = "오늘 배운 내용",
            difficult = "어려운 점",
            emotionScore = 80,
            emotion = "SATISFACTION",
            difficultyLevel = 3,
            updatedAt = System.currentTimeMillis()
        )
        tilDao.insertTil(til)
        val allTils = tilDao.getAllTils()
        assertEquals(allTils[0].title, "테스트 제목")
        assertEquals(allTils[0].id, 1L)
    }

    @Test
    @Throws(Exception::class)
    fun insertTilsAndGetAll() = runBlocking {
        val tils = listOf(
            TilEntity(id = 1L, createdAt = 1000L, title = "제목1", learned = "내용1", difficult = "", emotionScore = 50, emotion = "NORMAL", difficultyLevel = 1, updatedAt = 1000L),
            TilEntity(id = 2L, createdAt = 2000L, title = "제목2", learned = "내용2", difficult = "", emotionScore = 60, emotion = "NORMAL", difficultyLevel = 2, updatedAt = 2000L)
        )
        tilDao.insertTils(tils)
        val allTils = tilDao.getAllTils()
        assertEquals(allTils.size, 2)
        assertEquals(allTils[0].title, "제목2")
    }

    @Test
    @Throws(Exception::class)
    fun deleteTilById() = runBlocking {
        val til = TilEntity(id = 1L, createdAt = 1000L, title = "제목1", learned = "내용1", difficult = "", emotionScore = 50, emotion = "NORMAL", difficultyLevel = 1, updatedAt = 1000L)
        tilDao.insertTil(til)
        tilDao.deleteTilById(1L)
        val allTils = tilDao.getAllTils()
        assertEquals(allTils.size, 0)
    }
}
