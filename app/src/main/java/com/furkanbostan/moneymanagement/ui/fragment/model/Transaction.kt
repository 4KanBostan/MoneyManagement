package com.furkanbostan.moneymanagement.ui.fragment.model

import java.time.LocalDate

data class Transaction(val category:String ,val account:String, val amount:Float, val date: String, val comment:String, val image:Int) {
}