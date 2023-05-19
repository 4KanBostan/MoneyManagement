package com.furkanbostan.moneymanagement.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.databinding.FragmentDayViewCalendarBinding
import com.furkanbostan.moneymanagement.ui.adapter.DailyTransactionsAdapter
import com.furkanbostan.moneymanagement.ui.adapter.DayViewChildAdapter
import com.furkanbostan.moneymanagement.ui.adapter.DayViewParentAdapter
import com.furkanbostan.moneymanagement.ui.fragment.model.ParentRecycleView
import com.furkanbostan.moneymanagement.ui.fragment.model.Transaction
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.delay
import java.time.LocalDate


class DayViewCalendarFragment : Fragment() {
    private lateinit var binding: FragmentDayViewCalendarBinding
    private lateinit var parentRecyclerView: RecyclerView
    private lateinit var parentAdapter: DayViewParentAdapter
    private lateinit var childRecyclerView:RecyclerView
    private lateinit var childAdapter:DayViewChildAdapter
    private lateinit var transactionList :ArrayList<Transaction>
    private lateinit var parentList: ArrayList<ParentRecycleView>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentDayViewCalendarBinding.inflate(layoutInflater,container,false)

        parentRecyclerView = binding.parentRecylerView
        parentRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter = DayViewParentAdapter(ornekTrans())
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }



    fun ornekTrans():List<ParentRecycleView>{
        val t = Transaction("Eğlence","Banka Kartı",3500f,"22/3/2023","Oyun makinesi",R.drawable.cutlery)
        val t1 = Transaction("Eğlence","Banka Kartı",3500f,"22/3/2023","Oyun makinesi",R.drawable.cutlery)
        val t2 = Transaction("Eğlence","Banka Kartı",3500f,"22/3/2023","Oyun makinesi",R.drawable.cutlery)
        transactionList=ArrayList()
        transactionList.add(t)
        transactionList.add(t1)
        transactionList.add(t2)
        val date:LocalDate = LocalDate.now()
        val p = ParentRecycleView(date.minusDays(2),transactionList)
        val p1 = ParentRecycleView(date.plusDays(1),transactionList)
        val p2 = ParentRecycleView(date.plusDays(2),transactionList)
        parentList=ArrayList()
        parentList.add(p)
        parentList.add(p1)
        parentList.add(p2)

        return parentList
    }

}