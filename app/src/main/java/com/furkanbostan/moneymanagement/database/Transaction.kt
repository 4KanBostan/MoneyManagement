package com.furkanbostan.moneymanagement.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@Entity(tableName = "transaction")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int=0,
    @ColumnInfo(name = "category_id")
    var category_id:Int,
    @ColumnInfo(name = "account_id")
    var account_id:Int,
    @ColumnInfo(name = "amount")
    var amount:Float,
    @ColumnInfo(name = "date")
    var date:LocalDate,
    @ColumnInfo(name = "note")
    var note:String,
    @ColumnInfo(name = "type")
    var type:Boolean


)
