package com.furkanbostan.moneymanagement.ui.transactionRecord

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.databinding.FragmentAddTransactionBinding
import com.furkanbostan.moneymanagement.ui.calendarPage.CalendarViewCalendarFragment
import com.furkanbostan.moneymanagement.ui.calendarPage.DayViewCalendarFragment
import com.furkanbostan.moneymanagement.ui.calendarPage.MonthViewCalendarFragment
import com.furkanbostan.moneymanagement.ui.calendarPage.adapter.CalendarViewPagerAdapter
import com.furkanbostan.moneymanagement.ui.transactionRecord.adapter.TransactionRecordViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class AddTransactionFragment : Fragment() {
    private lateinit var binding:FragmentAddTransactionBinding
    private val fragmentList = ArrayList<Fragment>()
    private val fragmentNames= ArrayList<String>()
    private lateinit var viewPagerAdapter: TransactionRecordViewPagerAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddTransactionBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayoutConf()

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun tabLayoutConf(){
        fragmentList.add(IncomeRecordFragment())
        fragmentList.add(ExpenseRecordFragment())
        fragmentList.add(TransferRecordFragment())
        viewPagerAdapter= TransactionRecordViewPagerAdapter(fragmentList,childFragmentManager, lifecycle = lifecycle)
        binding.recordViewPager2.adapter=viewPagerAdapter

        fragmentNames.add("Gelir")
        fragmentNames.add("Gider")
        fragmentNames.add("Havale")


        TabLayoutMediator(binding.recordTabLayout,binding.recordViewPager2){tab,position ->
            tab.text= fragmentNames[position]
        }.attach()
    }



}