package com.furkanbostan.moneymanagement.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.furkanbostan.moneymanagement.database.Expense
import com.furkanbostan.moneymanagement.database.Income

@Dao
interface ExpenseDao{
    @Insert
    suspend fun insert(expense: Expense)

    @Insert
    suspend fun insertAll(vararg expense: Expense)

    @Query("SELECT * FROM expense")
    suspend fun getAllGoals():List<Expense>

    @Query("DELETE FROM expense")
    suspend fun deleteAll()

    @Query("DELETE FROM expense WHERE id = :id")
    suspend fun deleteById(id:Int)

    @Query("SELECT * FROM income WHERE date_month = :month")
    suspend fun getAllOfMonth(month:String):List<Expense>

}