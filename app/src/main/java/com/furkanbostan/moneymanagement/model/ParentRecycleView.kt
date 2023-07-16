package com.furkanbostan.moneymanagement.model

import java.time.LocalDate

data class ParentRecycleView(val date: LocalDate, val itemList:ArrayList<Transaction>) {
}