package com.furkanbostan.moneymanagement.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.databinding.FragmentDayViewCalendarBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.delay


class DayViewCalendarFragment : Fragment() {
    private lateinit var binding: FragmentDayViewCalendarBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentDayViewCalendarBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.example.setOnClickListener {
            val tabs = activity?.findViewById<TabLayout>(R.id.tablayout)
            tabs?.getTabAt(1)?.select()
            val sp :SharedPreferences= this.requireActivity().getSharedPreferences("deneme",Context.MODE_PRIVATE)
            val edit = sp.edit()
            edit.putString("d1",sp.getString("d1","assad").toString())
            edit.commit()

        }
    }


}