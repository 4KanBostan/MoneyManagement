package com.furkanbostan.moneymanagement.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

@Entity(tableName = "goal")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int=0,
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "amount")
    var amount:Float,
    @ColumnInfo(name = "target_goal")
    var target_goal:Float,
    @ColumnInfo(name = "target_date")
    var target_date:LocalDate,
    @ColumnInfo(name = "image_url")
    var image_url:String
)