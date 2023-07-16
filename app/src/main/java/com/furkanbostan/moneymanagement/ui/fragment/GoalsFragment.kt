package com.furkanbostan.moneymanagement.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.database.Goal
import com.furkanbostan.moneymanagement.databinding.FragmentGoalsBinding
import com.furkanbostan.moneymanagement.ui.adapter.GoalsAdapter
import com.furkanbostan.moneymanagement.model.Goals
import com.furkanbostan.moneymanagement.ui.BaseCoroutine
import java.time.LocalDate


class GoalsFragment : BaseCoroutine() {
        private lateinit var binding:FragmentGoalsBinding
        private lateinit var goalsList:ArrayList<Goals>
        private lateinit var localDate: LocalDate
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentGoalsBinding.inflate(layoutInflater,container,false)
        setRcv()
        return binding.root
    }



    fun setRcv(){
        binding.goalsRecycler.apply {
            layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter=GoalsAdapter(context,addItem())
        }
    }

    fun addItem():ArrayList<Goals>{
        localDate= LocalDate.now()
        goalsList= ArrayList()
        goalsList.add(Goals("Araba",50,2500,4000,R.drawable.car,localDate))
        goalsList.add(Goals("Araba",50,2500,4000,R.drawable.car,localDate))
        goalsList.add(Goals("Araba",50,2500,4000,R.drawable.car,localDate))
        goalsList.add(Goals("Araba",50,2500,4000,R.drawable.car,localDate))

        return goalsList
    }


    private fun storeInRoom(list: List<Goal>){

    }

}