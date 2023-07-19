package com.furkanbostan.moneymanagement.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int=0,
    @ColumnInfo(name = "category_id")
    var category_id:Int,
    @ColumnInfo(name = "account_id")
    var account_id:Int,
    @ColumnInfo(name = "amount")
    var amount:Float,
    @ColumnInfo(name = "note")
    var note:String,
    @ColumnInfo(name = "date")
    var date:String,
    @ColumnInfo(name = "date_day")
    var date_day:String,
    @ColumnInfo(name = "date_month")
    var date_month:String,
    @ColumnInfo(name = "date_year")
    var date_year:String
    )