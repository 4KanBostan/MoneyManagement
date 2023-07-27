package com.furkanbostan.moneymanagement.model

import com.furkanbostan.moneymanagement.database.Transactions
import java.time.LocalDate

data class ParentRecycleView(val date: LocalDate, val itemList:ArrayList<Transactions>) {
}