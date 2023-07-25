package com.furkanbostan.moneymanagement.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class GoalAndCategory(
    @Embedded val goal: Goal,
    @Relation(
        parentColumn = "goal_category_id",
        entityColumn = "id"
    )
    val category: GoalCategory
)