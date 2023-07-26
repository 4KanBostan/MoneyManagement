package com.furkanbostan.moneymanagement.ui.calendarPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.databinding.LayoutBottomSheetBinding
import com.furkanbostan.moneymanagement.ui.calendarPage.adapter.DailyTransactionsAdapter
import com.furkanbostan.moneymanagement.model.Transaction
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate

class ShowDayFragment(val date: String) :BottomSheetDialogFragment(){
    private lateinit var binding: LayoutBottomSheetBinding
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter: DailyTransactionsAdapter
    private lateinit var transactionList :ArrayList<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL,R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LayoutBottomSheetBinding.inflate(inflater,container,false)

        binding.dateTv.text=date.toString()
        recyclerView = binding.recyclerDialog
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = DailyTransactionsAdapter(requireContext(),ornekTrans())
        recyclerView.adapter=adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    fun ornekTrans():List<Transaction>{
        val t = Transaction("Eğlence","Banka Kartı",3500f,"22/3/2023","Oyun makinesi",R.drawable.cutlery)
        val t1 = Transaction("Eğlence","Banka Kartı",3500f,"22/3/2023","Oyun makinesi",R.drawable.cutlery)
        val t2 = Transaction("Eğlence","Banka Kartı",3500f,"22/3/2023","Oyun makinesi",R.drawable.cutlery)
        transactionList=ArrayList()
        transactionList.add(t)
        transactionList.add(t1)
        transactionList.add(t2)
        return transactionList
    }

}