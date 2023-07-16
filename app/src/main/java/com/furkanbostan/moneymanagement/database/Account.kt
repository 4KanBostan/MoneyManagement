package com.furkanbostan.moneymanagement.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int=0,
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "income")
    var income:Float,
    @ColumnInfo(name = "expense")
    var expense:Float,
    @ColumnInfo(name = "balance")
    var balance:Float
)