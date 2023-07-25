package com.furkanbostan.moneymanagement.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.furkanbostan.moneymanagement.database.Goal
import com.furkanbostan.moneymanagement.database.GoalCategory

@Dao
interface GoalCategoryDao {

    @Insert
    suspend fun insert(goalCategory: GoalCategory)

    @Insert
    suspend fun insertAll(vararg goalCategory: GoalCategory)

    @Query("SELECT * FROM goalCategory")
    suspend fun getAllGoals():List<GoalCategory>

    @Query("DELETE FROM goalCategory")
    suspend fun deleteAll()

    @Query("DELETE FROM goalCategory WHERE id = :id")
    suspend fun deleteById(id:Int)
}