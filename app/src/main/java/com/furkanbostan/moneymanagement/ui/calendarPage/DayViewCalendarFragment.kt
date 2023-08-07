package com.furkanbostan.moneymanagement.ui.calendarPage

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        //Fragment başlatıldığında günün verisine göre adapter çağrıldı
        todayCallAdapter()


        binding.monthTv.setOnClickListener {
            openCustomDialog()
        }

        transactions.observe(viewLifecycleOwner) { value ->
            groupTransactionsByMonth()
            if(!value.isEmpty()){
                totalStats(value)
            }else{
                binding.incomeTv.text="0"
                binding.expenseTv.text="0"
                binding.totalTv.text= "0"
            }

        }

        binding.dayViewNextMonth.setOnClickListener {
            val index= calendar.get(Calendar.MONTH)
            calendar.set(Calendar.MONTH,index+1)
            binding.yearTv.text= calendar.get(Calendar.YEAR).toString()
            binding.monthTv.text=SimpleDateFormat("MMMM", Locale("tr")).format(calendar.time)
            filterTransactionsForMonthAndYear("%02d".format(calendar.get(Calendar.MONTH)+1),calendar.get(Calendar.YEAR).toString())
        }

        binding.dayViewPreviousMonth.setOnClickListener {
            val index= calendar.get(Calendar.MONTH)
            calendar.set(Calendar.MONTH,index-1)
            binding.yearTv.text= calendar.get(Calendar.YEAR).toString()
            binding.monthTv.text=SimpleDateFormat("MMMM", Locale("tr")).format(calendar.time)
            filterTransactionsForMonthAndYear("%02d".format(calendar.get(Calendar.MONTH)+1),calendar.get(Calendar.YEAR).toString())

        }
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
    private fun totalStats(value: ArrayList<TransactionsWithCategoryAndAccount>) {
        var incomeCount= 0f
        var expenseCount= 0f
        var balanceCount = 0f
        for (i in value){
            if(i.transaction.type){
                incomeCount+= i.transaction.amount
            }
            else expenseCount+= i.transaction.amount
        }
        balanceCount= incomeCount - expenseCount
        binding.incomeTv.text=incomeCount.toInt().toString()
        binding.expenseTv.text=expenseCount.toInt().toString()
        binding.totalTv.text= balanceCount.toInt().toString()
    }


}