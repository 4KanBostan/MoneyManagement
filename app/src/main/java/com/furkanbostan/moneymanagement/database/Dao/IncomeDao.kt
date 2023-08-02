package com.furkanbostan.moneymanagement.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.furkanbostan.moneymanagement.database.Goal
import com.furkanbostan.moneymanagement.database.Income
import com.furkanbostan.moneymanagement.database.IncomeWithCategoryAndAccount
import com.furkanbostan.moneymanagement.database.TransactionsWithCategoryAndAccount

@Dao
interface IncomeDao {
    @Insert
    suspend fun insert(income: Income)

    @Insert
    suspend fun insertAll(vararg income: Income)

    @Query("SELECT * FROM income")
    suspend fun getAllGoals():List<Income>

    @Query("DELETE FROM income")
    suspend fun deleteAll()

    @Query("DELETE FROM income WHERE id = :id")
    suspend fun deleteById(id:Int)

    @Query("SELECT * FROM income WHERE date_month = :month")
    suspend fun getAllOfMonth(month:String):List<Income>

    @Transaction
    @Query("SELECT * FROM income WHERE income.date_year = :year AND income.date_month = :month")
    suspend fun getIncomeWithCategoryAndAccountofMounth(month:String, year:String):List<IncomeWithCategoryAndAccount>

}