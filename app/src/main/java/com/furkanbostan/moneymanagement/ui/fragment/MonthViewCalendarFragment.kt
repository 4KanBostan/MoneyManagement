package com.furkanbostan.moneymanagement.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.databinding.FragmentMonthViewCalendarBinding


class MonthViewCalendarFragment : Fragment() {
    private lateinit var binding: FragmentMonthViewCalendarBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMonthViewCalendarBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sp:SharedPreferences= this.requireActivity().getSharedPreferences("deneme",Context.MODE_PRIVATE)
        if(sp.getString("d1",null)!=null){
            binding.mvText.text=sp.getString("d1",null)
        }

    }


}