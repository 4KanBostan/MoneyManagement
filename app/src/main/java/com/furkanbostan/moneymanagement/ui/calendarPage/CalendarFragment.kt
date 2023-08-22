package com.furkanbostan.moneymanagement.ui.calendarPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furkanbostan.moneymanagement.databinding.FragmentCalendarBinding
import com.furkanbostan.moneymanagement.ui.calendarPage.adapter.CalendarViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class CalendarFragment : Fragment(){
    private lateinit var binding: FragmentCalendarBinding
    private val fragmentListesi = ArrayList<Fragment>()
    private val fragmentNames= ArrayList<String>()
    private lateinit var viewPagerAdapter: CalendarViewPagerAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentCalendarBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayoutConf()
    }




    private fun tabLayoutConf(){
        fragmentListesi.add(CalendarViewCalendarFragment())
        fragmentListesi.add(DayViewCalendarFragment())
        fragmentListesi.add(MonthViewCalendarFragment())
        fragmentListesi.add(YearViewCalendarFragment())
        viewPagerAdapter= CalendarViewPagerAdapter(fragmentListesi,childFragmentManager, lifecycle = lifecycle)
        binding.viewPager2.adapter=viewPagerAdapter
        // val tabview = binding.tablayout
        /* tabview.getTabAt(0)?.text ="First"
         tabview.getTabAt(1)?.text ="Second"
         tabview.getTabAt(2)?.text ="Thirt"
 */
        fragmentNames.add("Calendar")
        fragmentNames.add("Day")
        fragmentNames.add("Month")
        fragmentNames.add("Year")

        TabLayoutMediator(binding.tablayout,binding.viewPager2){tab,position ->
            tab.text= fragmentNames[position]
        }.attach()
    }

}