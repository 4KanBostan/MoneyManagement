package com.furkanbostan.moneymanagement.ui.calendarPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.database.Transactions
import com.furkanbostan.moneymanagement.database.TransactionsWithCategoryAndAccount
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.FragmentShowDayBinding
import com.furkanbostan.moneymanagement.ui.calendarPage.adapter.DailyTransactionsAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class ShowDayFragment(val calendar:Calendar) :BottomSheetDialogFragment(),CoroutineScope{
    private lateinit var binding: FragmentShowDayBinding
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter: DailyTransactionsAdapter
    private lateinit var transactionList :ArrayList<TransactionsWithCategoryAndAccount>
    private lateinit var groupedTransactionsOfDay : TreeMap<String,ArrayList<TransactionsWithCategoryAndAccount>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL,R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShowDayBinding.inflate(inflater,container,false)
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale("tr"))
        setDateTv()

        filterTransacitonOfDate(formatter.format(calendar.time))


        binding.nextDay.setOnClickListener {
            calendar.add(Calendar.DAY_OF_MONTH,1)
            filterTransacitonOfDate(formatter.format(calendar.time))
        }
        binding.previousDay.setOnClickListener {
            calendar.add(Calendar.DAY_OF_MONTH,-1)
            filterTransacitonOfDate(formatter.format(calendar.time))
        }

        return binding.root
    }

    private fun setDateTv() {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale("tr"))
        binding.dateTv.text= formatter.format(calendar.time)
    }

    private fun setRcv(list:ArrayList<TransactionsWithCategoryAndAccount>) {
        recyclerView = binding.recyclerDialog
        recyclerView.layoutManager = LinearLayoutManager(context)

          adapter = DailyTransactionsAdapter(requireContext(),list)
          recyclerView.adapter=adapter

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun filterTransacitonOfDate(date:String){
        setDateTv()
        transactionList= ArrayList()
        launch {
            val dao= ManagDataBase(requireContext()).transactionsDao()
            transactionList.clear()
            transactionList.addAll(dao.getTransactionsWithCategoryAndAccountOfDate(date) as ArrayList<TransactionsWithCategoryAndAccount>)
            setRcv(transactionList)
            setTotalStats(transactionList)
        }
    }

    private fun setTotalStats(list: ArrayList<TransactionsWithCategoryAndAccount>) {
        var incomeCount= 0f
        var expenseCount= 0f

        for (i in list){
            if (i.transaction.type) incomeCount+=i.transaction.amount
            else expenseCount+= i.transaction.amount
        }
        binding.incomeTvShowDay.text=incomeCount.toInt().toString()
        binding.expenseTvShowDay.text=expenseCount.toInt().toString()
    }


    private val job= Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}