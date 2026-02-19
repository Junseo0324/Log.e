package com.devhjs.loge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devhjs.loge.data.local.entity.MonthlyReviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthlyReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonthlyReview(review: MonthlyReviewEntity)

    @Query("SELECT * FROM monthly_reviews WHERE yearMonth = :yearMonth LIMIT 1")
    fun getMonthlyReview(yearMonth: String): Flow<MonthlyReviewEntity?>
    
    @Query("SELECT * FROM monthly_reviews WHERE userId = :userId AND yearMonth = :yearMonth LIMIT 1")
    suspend fun getMonthlyReviewSync(userId: String, yearMonth: String): MonthlyReviewEntity?
}
