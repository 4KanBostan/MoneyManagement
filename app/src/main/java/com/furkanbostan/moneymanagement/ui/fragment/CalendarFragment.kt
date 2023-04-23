package com.furkanbostan.moneymanagement.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.furkanbostan.moneymanagement.R


class CalendarFragment : Fragment() {
    private val fragmentListesi = ArrayList<Fragment>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentListesi.add(DayViewCalendarFragment())
        fragmentListesi.add(MonthViewCalendarFragment())
        fragmentListesi.add(YearViewCalendarFragment())
    }




}