package com.furkanbostan.moneymanagement.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.furkanbostan.moneymanagement.database.Account
import com.furkanbostan.moneymanagement.database.Category

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(category: Category)

    @Insert
    suspend fun insertAll(vararg category: Category)

    @Query("SELECT * FROM category")
    suspend fun getAllCategory():List<Category>

    @Query("DELETE FROM category")
    suspend fun deleteAll()

    @Query("DELETE FROM category WHERE id = :id")
    suspend fun deleteById(id:Int)

    @Query("SELECT * FROM category WHERE name = :name")
    suspend fun getCategoryByCtegoryName(name:String):Category

    @Query("SELECT * FROM category WHERE type = 1")
    suspend fun getAllIncomeCategory():List<Category>

    @Query("SELECT * FROM category WHERE type = 0")
    suspend fun getAllExpenseCategory():List<Category>



}