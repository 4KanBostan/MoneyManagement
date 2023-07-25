package com.furkanbostan.moneymanagement.database

import androidx.room.*

@Entity(tableName = "goal")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int=0,
    @ColumnInfo(name = "name")
    var description:String,
    @ColumnInfo(name = "amount")
    var amount:Float,
    @ColumnInfo(name = "target_goal")
    var target_goal:Float,
    @ColumnInfo(name = "target_date")
    var target_date:String,
    @ColumnInfo(name = "date_day")
    var date_day:String,
    @ColumnInfo(name = "date_month")
    var date_month:String,
    @ColumnInfo(name = "date_year")
    var date_year:String,
    @ColumnInfo(name = "goal_category_id")
    var goal_category_id:Int
)