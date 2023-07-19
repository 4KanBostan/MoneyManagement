package com.furkanbostan.moneymanagement.database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.furkanbostan.moneymanagement.database.Goal

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
}