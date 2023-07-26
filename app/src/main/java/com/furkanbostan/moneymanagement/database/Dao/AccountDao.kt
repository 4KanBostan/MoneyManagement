package com.furkanbostan.moneymanagement.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.furkanbostan.moneymanagement.database.Account
import com.furkanbostan.moneymanagement.database.Goal

@Dao
interface  AccountDao {

    @Insert
    suspend fun insert(account: Account)

    @Insert
    suspend fun insertAll(vararg account: Account)

    @Query("SELECT * FROM account")
    suspend fun getAllAccount():List<Account>

    @Query("DELETE FROM account")
    suspend fun deleteAll()

    @Query("DELETE FROM account WHERE id = :id")
    suspend fun deleteById(id:Int)
}