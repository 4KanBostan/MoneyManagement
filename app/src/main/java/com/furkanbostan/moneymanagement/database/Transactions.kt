package com.furkanbostan.moneymanagement.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transactions(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int=0,
    @ColumnInfo(name = "category_id")
    var category_id:Int?,
    @ColumnInfo(name = "first_account_id")
    var first_account_id:Int,
    @ColumnInfo(name = "second_account_id")
    var second_account_id:Int?,
    @ColumnInfo(name = "amount")
    var amount:Float,
    @ColumnInfo(name = "date")
    var date:String,
    @ColumnInfo(name = "note")
    var note:String,
    @ColumnInfo(name = "date_day")
    var date_day:String,
    @ColumnInfo(name = "date_month")
    var date_month:String,
    @ColumnInfo(name = "date_year")
    var date_year:String,
    @ColumnInfo(name = "type")
    var type:Int,


)
