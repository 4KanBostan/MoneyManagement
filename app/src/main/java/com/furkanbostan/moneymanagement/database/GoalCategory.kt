package com.furkanbostan.moneymanagement.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goalCategory")
data class GoalCategory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int=0,
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "image_url")
    var image_url:Int,
)