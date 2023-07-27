package com.furkanbostan.moneymanagement.database

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionsWithCategoryAndAccount(
    @Embedded val transaction: Transactions,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: Category,
    @Relation(
        parentColumn = "account_id",
        entityColumn = "id"
    )
    val account: Account
)
