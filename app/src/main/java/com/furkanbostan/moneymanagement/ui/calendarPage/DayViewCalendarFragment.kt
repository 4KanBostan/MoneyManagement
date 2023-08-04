package com.furkanbostan.moneymanagement.ui.calendarPage

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.R
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


class DayViewCalendarFragment : BaseFragment(), CalendarDialog.OnDateSelectedListener {
    private lateinit var binding: FragmentDayViewCalendarBinding
    private lateinit var parentList: ArrayList<ParentRecycleView>
    private val today = LocalDate.now()
    private val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Istanbul"))
    private val transactions = MutableLiveData<ArrayList<TransactionsWithCategoryAndAccount>>()
    private var groupedTransactions = TreeMap<String, ArrayList<TransactionsWithCategoryAndAccount>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDayViewCalendarBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.monthTv.setOnClickListener {
            openCustomDialog()
        }

        transactions.observe(viewLifecycleOwner) { value ->
            groupTransactionsByMonth()
        }

        //Fragment başlatıldığında günün verisine göre adapter çağrıldı
        todayCallAdapter()


    }

    private fun todayCallAdapter() {
        calendar.set(Calendar.MONTH,today.monthValue)
        calendar.set(Calendar.YEAR,today.year)
        val month = DateTimeFormatter.ofPattern("MMMM ", Locale("tr")).format(today)
        binding.monthTv.text = month
        val year = DateTimeFormatter.ofPattern("YYYY", Locale("tr")).format(today)
        binding.yearTv.text = year
        filterTransactionsForMonthAndYear("%02d".format(today.monthValue), today.year.toString())

    }


    private fun filterTransactionsForMonthAndYear(month: String, year: String) {
        println(month)
        launch {
            val dao = ManagDataBase(requireContext()).transactionsDao()
            transactions.value?.clear()
            transactions.postValue(dao.getTransactionsWithCategoryAndAccountofMounth(month,year.trim()) as ArrayList<TransactionsWithCategoryAndAccount>)
        }
        calendar.set(Calendar.MONTH,(month.trimStart('0').toInt())-1)
        calendar.set(Calendar.YEAR,today.year)
    }


    private fun groupTransactionsByMonth() {
        groupedTransactions.values.clear()
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
        updateRcv(groupedTransactions)
    }

    private fun updateRcv(groupedTransactions: TreeMap<String, ArrayList<TransactionsWithCategoryAndAccount>>) {
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

    override fun onDateSelected(year: String, monthIndex: Int) {
        binding.yearTv.text=year
        calendar.set(Calendar.MONTH, monthIndex)
        binding.monthTv.text=SimpleDateFormat("MMMM", Locale("tr")).format(calendar.time)
        filterTransactionsForMonthAndYear("%02d".format(monthIndex+1),year)
    }

    private fun openCustomDialog() {
        val dialog = CalendarDialog(requireContext(),calendar)
        dialog.onDateSelectedListener = this
        //dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT)
        dialog.window?.setGravity(Gravity.TOP)
        dialog.show()
    }


}