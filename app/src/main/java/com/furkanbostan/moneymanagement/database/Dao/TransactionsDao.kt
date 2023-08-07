package com.furkanbostan.moneymanagement.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.furkanbostan.moneymanagement.database.Transactions
import com.furkanbostan.moneymanagement.database.TransactionsWithCategoryAndAccount

@Dao
interface TransactionsDao {
    @Insert
    suspend fun insert(transactions: Transactions)

    @Insert
    suspend fun insertAll(vararg transactions: Transactions)

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransaction():List<Transactions>

    @Query("DELETE FROM transactions")
    suspend fun deleteAll()

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id:Int)

    @Query("SELECT * FROM transactions WHERE date_year = :year AND date_month = :month")
    suspend fun getTransofMonthAndYear(month:String, year:String):List<Transactions>

    @Transaction
    @Query("SELECT * FROM transactions WHERE transactions.date_year = :year AND transactions.date_month = :month")
    suspend fun getTransactionsWithCategoryAndAccountofMounth(month:String, year:String):List<TransactionsWithCategoryAndAccount>

    @Transaction
    @Query("SELECT * FROM transactions")
    suspend fun getTransactionsWithCategoryAndAccount():List<TransactionsWithCategoryAndAccount>

    @Transaction
    @Query("SELECT * FROM transactions ORDER BY id DESC LIMIT 4")
    suspend fun getLastTransactionsWithCategoryAndAccount():List<TransactionsWithCategoryAndAccount>

}