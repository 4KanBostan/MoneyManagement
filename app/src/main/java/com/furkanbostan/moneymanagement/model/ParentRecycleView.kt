package com.furkanbostan.moneymanagement.model

import com.furkanbostan.moneymanagement.database.Transactions
import com.furkanbostan.moneymanagement.database.TransactionsWithCategoryAndAccount
import java.time.LocalDate

data class ParentRecycleView(val date: String, val itemList:ArrayList<TransactionsWithCategoryAndAccount>) {
}