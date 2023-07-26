package com.furkanbostan.moneymanagement.ui.calendarPage.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class CalendarViewPagerAdapter(val fragmentListesi: List<Fragment>,fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
       return fragmentListesi.size
    }

    override fun createFragment(position: Int): Fragment {
       return fragmentListesi[position]
    }


}