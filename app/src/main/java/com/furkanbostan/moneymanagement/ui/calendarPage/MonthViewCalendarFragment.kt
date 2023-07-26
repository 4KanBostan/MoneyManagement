package com.furkanbostan.moneymanagement.ui.calendarPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.databinding.FragmentMonthViewCalendarBinding
import com.furkanbostan.moneymanagement.ui.calendarPage.adapter.MonthViewAdapter
import com.furkanbostan.moneymanagement.model.MonthTransaction


class MonthViewCalendarFragment : Fragment() {
    private lateinit var binding: FragmentMonthViewCalendarBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var monthList: ArrayList<MonthTransaction>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMonthViewCalendarBinding.inflate(layoutInflater,container,false)

        recyclerView= binding.recylerViewMonthView
        recyclerView.apply {
            layoutManager= LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            adapter = MonthViewAdapter(months())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    fun months():ArrayList<MonthTransaction>{
        val ocak= MonthTransaction("Ocak",1300,2000,700)
        val subat= MonthTransaction("Şubat",1300,2000,700)
        val mart= MonthTransaction("Mart",1300,2000,700)
        val nisan= MonthTransaction("Nisan",1300,2000,700)
        val mayıs= MonthTransaction("Mayıs",1300,2000,700)
        val haziran= MonthTransaction("Haziran",1300,2000,700)
        val temmuz= MonthTransaction("Temmuz",1300,2000,700)
        val agustos= MonthTransaction("Ağustos",1300,2000,700)
        val eylül= MonthTransaction("Eylül",1300,2000,700)
        val ekim= MonthTransaction("Ekim",1300,2000,700)
        val kasım= MonthTransaction("Kasım",1300,2000,700)
        val aralık= MonthTransaction("Aralık",1300,2000,700)

        monthList= ArrayList()

        monthList.add(ocak)
        monthList.add(subat)
        monthList.add(mart)
        monthList.add(nisan)
        monthList.add(mayıs)
        monthList.add(haziran)
        monthList.add(temmuz)
        monthList.add(agustos)
        monthList.add(eylül)
        monthList.add(ekim)
        monthList.add(kasım)
        monthList.add(aralık)


        return monthList
    }
}