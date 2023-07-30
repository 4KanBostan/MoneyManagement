package com.furkanbostan.moneymanagement.ui.calendarPage

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.database.Transactions
import com.furkanbostan.moneymanagement.database.TransactionsWithCategoryAndAccount
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.FragmentDayViewCalendarBinding
import com.furkanbostan.moneymanagement.model.ParentRecycleView
import com.furkanbostan.moneymanagement.ui.BaseFragment
import com.furkanbostan.moneymanagement.ui.calendarPage.adapter.DayViewParentAdapter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class DayViewCalendarFragment : BaseFragment() {
    private lateinit var binding: FragmentDayViewCalendarBinding
    private lateinit var parentList: ArrayList<ParentRecycleView>
    private val today = LocalDate.now()
    private val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Istanbul"))
    private var datePickerDialog: DatePickerDialog? = null
    private val transactions = MutableLiveData<ArrayList<TransactionsWithCategoryAndAccount>>()
    private var groupedTransactions = TreeMap<String, ArrayList<TransactionsWithCategoryAndAccount>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDayViewCalendarBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.monthTv.setOnClickListener {
            showDatePickerDialog()
        }

        transactions.observe(viewLifecycleOwner) { value ->
            groupTransactionsByMonth()
        }

        binding.linearLayoutDayview.setOnClickListener {
            datePickerDialog?.show()
        }
        //Fragment başlatıldığında günün verisine göre adapter çağrıldı
        todayCallAdapter()


    }

    private fun todayCallAdapter() {
        val month = DateTimeFormatter.ofPattern("MMMM ", Locale("tr")).format(today)
        binding.monthTv.text = month
        val year = DateTimeFormatter.ofPattern("YYYY", Locale("tr")).format(today)
        binding.yearTv.text = year
        filterTransactionsForMonthAndYear("%02d".format(today.monthValue), today.year.toString())

    }



    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, _ ->
                calendar.set(selectedYear, selectedMonth, 1) // Seçilen tarihi 1. gün olarak ayarla
                val formattedMonth = calendar.get(Calendar.MONTH)
                updateTransactionsforMonthAndYear(
                    "%02d".format(calendar.get(Calendar.MONTH)+1),
                    calendar.get(Calendar.YEAR).toString()
                )
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun updateTransactionsforMonthAndYear(month: String, year: String) {
        val displayedMonth = SimpleDateFormat("MMMM", Locale("tr")).format(calendar.time)
        binding.monthTv.text = displayedMonth
        binding.yearTv.text = year

        // Seçilen ayda olan işlemleri yeniden yükle
        filterTransactionsForMonthAndYear(month, year)

    }


    private fun filterTransactionsForMonthAndYear(month: String, year: String) {
        launch {
            val dao = ManagDataBase(requireContext()).transactionsDao()
            transactions.value?.clear()
            transactions.postValue(dao.getTransactionsWithCategoryAndAccountofMounth(month,year) as ArrayList<TransactionsWithCategoryAndAccount>)
        }
    }


    private fun groupTransactionsByMonth() {
        groupedTransactions.values.clear()
        println("transactions" + transactions.value!!.size)
        for (transaction in transactions.value!!) {
            val dateDay = transaction.transaction.date_day
            if (groupedTransactions.containsKey(dateDay)) {
                groupedTransactions[dateDay]?.add(transaction)
            } else {
                val newList = ArrayList<TransactionsWithCategoryAndAccount>()
                newList.add(transaction)
                groupedTransactions[dateDay] = newList
            }
        }
        println(groupedTransactions.size)
        updateRcv(groupedTransactions)
    }

    private fun updateRcv(groupedTransactions: TreeMap<String, ArrayList<TransactionsWithCategoryAndAccount>>) {
        println("groupedTransactions" +groupedTransactions.size)
        parentList =ArrayList()
        parentList.clear()
        for (i in groupedTransactions){
            parentList.add(ParentRecycleView(i.key,i.value))
        }
        binding.parentRecylerView.apply {
            layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter= DayViewParentAdapter(parentList)
        }

    }
}