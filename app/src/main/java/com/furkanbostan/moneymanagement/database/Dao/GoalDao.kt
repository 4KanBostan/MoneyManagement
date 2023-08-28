package com.furkanbostan.moneymanagement.database.Dao

import androidx.room.*
import com.furkanbostan.moneymanagement.database.Goal
import com.furkanbostan.moneymanagement.database.GoalAndCategory
import com.furkanbostan.moneymanagement.database.TransactionsWithCategoryAndAccount

@Dao
interface GoalDao {
    @Insert
    suspend fun insert(goal:Goal)

    @Insert
    suspend fun insertAll(vararg goals :Goal)

    @Query("SELECT * FROM goal")
    suspend fun getAllGoals():List<Goal>

    @Query("DELETE FROM goal")
    suspend fun deleteAll()

    @Query("DELETE FROM goal WHERE id = :id")
    suspend fun deleteById(id:Int)

    @Transaction
    @Query("SELECT * FROM goal")
    suspend fun getGoalAndCategory() : List<GoalAndCategory>

    @Transaction
    @Query("SELECT * FROM goal ORDER BY id DESC LIMIT 4")
    suspend fun getLastGoalAndCategory():List<GoalAndCategory>

}