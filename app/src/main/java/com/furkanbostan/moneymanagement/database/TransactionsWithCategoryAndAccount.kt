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
        parentColumn = "first_account_id",
        entityColumn = "id"
    )
    val firstAccount: Account,
    @Relation(
        parentColumn = "second_account_id",
        entityColumn = "id"
    )
    val secondAccount: Account
)
