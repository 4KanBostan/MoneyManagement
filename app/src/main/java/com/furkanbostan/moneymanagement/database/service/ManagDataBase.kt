package com.furkanbostan.moneymanagement.database.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.furkanbostan.moneymanagement.database.*
import com.furkanbostan.moneymanagement.database.Dao.*

@Database(entities = arrayOf(Transactions::class,Account::class, Category::class,Income::class,Expense::class,
            Goal::class,GoalCategory::class), version = 2 )
abstract class ManagDataBase:RoomDatabase() {

    abstract fun transactionsDao():TransactionsDao
    abstract fun accountDao():AccountDao
    abstract fun categoryDao():CategoryDao
    abstract fun incomeDao():IncomeDao
    abstract fun expenseDao():ExpenseDao
    abstract fun goalDao():GoalDao
    abstract fun goalCategoryDao():GoalCategoryDao




    companion object{
        @Volatile private var instance:ManagDataBase?=null

        private val lock=Any()

        operator fun invoke(context: Context)= instance?: synchronized(lock){
            instance?: makeDatabase(context).also {
                instance=it
            }
        }


        private fun makeDatabase(context: Context)=Room.databaseBuilder(
            context.applicationContext,ManagDataBase::class.java,"managDatabase"
                ).build()
    }
}