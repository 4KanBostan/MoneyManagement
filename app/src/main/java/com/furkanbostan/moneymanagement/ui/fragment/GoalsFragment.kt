package com.furkanbostan.moneymanagement.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.database.Goal
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.FragmentGoalsBinding
import com.furkanbostan.moneymanagement.ui.BaseFragment
import com.furkanbostan.moneymanagement.ui.adapter.GoalsAdapter
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.time.LocalDate


class GoalsFragment : BaseFragment() {
        private lateinit var binding:FragmentGoalsBinding
        private lateinit var goalsList:ArrayList<Goal>
        private lateinit var localDate: LocalDate
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentGoalsBinding.inflate(layoutInflater,container,false)
        //storeInRoom()
        totalSaving()
        //addItem()
        getAllGoal()

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_goalsFragment_to_addGoalFragment)
        }
        return binding.root
    }


     fun totalSaving(){
        var list= listOf<Goal>()
        var temp =0f
         launch {
            val dao=ManagDataBase(requireContext()).goalDao()
            list=dao.getAllGoals()
            for (i in list){
                temp+=i.amount
            }
            binding.totalSaving.text=temp.toInt().toString()
        }


    }


    fun setRcv( list: List<Goal>){
        val liste:ArrayList<Goal> =list as ArrayList<Goal>
        binding.goalsRecycler.apply {
            layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter=GoalsAdapter(context,liste)
        }
    }

    fun addItem():ArrayList<Goal>{
        localDate= LocalDate.now()



        goalsList= ArrayList()
        goalsList.add(Goal(0,"Araba",70f,2500f,localDate.toString(),localDate.dayOfMonth.toString(),localDate.month.toString(),localDate.year.toString(),R.drawable.car))
        goalsList.add(Goal(0,"Araba",50f,2500f,localDate.toString(),localDate.dayOfMonth.toString(),localDate.month.toString(),localDate.year.toString(),R.drawable.car))
        goalsList.add(Goal(0,"Araba",30f,2500f,localDate.toString(),localDate.dayOfMonth.toString(),localDate.month.toString(),localDate.year.toString(),R.drawable.car))
        goalsList.add(Goal(0,"Araba",20f,2500f,localDate.toString(),localDate.dayOfMonth.toString(),localDate.month.toString(),localDate.year.toString(),R.drawable.car))
        storeAllInRoom(goalsList)

        return goalsList
    }

    private fun storeInRoom(){
        localDate= LocalDate.now()
        launch {
            val dao=ManagDataBase(requireContext()).goalDao()
            dao.insert(Goal(0,"Araba",50f,2500f,localDate.toString(),localDate.dayOfMonth.toString(),localDate.month.toString(),localDate.year.toString(),R.drawable.car))
            println("buraya igriş yaptı")
        }
    }
    private fun storeAllInRoom(list: List<Goal>){
        launch {
            val dao=ManagDataBase(requireContext()).goalDao()
            dao.insertAll(*list.toTypedArray())
            println("buraya igriş yaptı")
        }
    }

    private fun getAllGoal(){
        var list= listOf<Goal>()
        launch {
            val dao=ManagDataBase(requireContext()).goalDao()
            list=dao.getAllGoals()
            setRcv(list)
        }

    }

    private fun deleteAll(){
        launch {
            val dao=ManagDataBase(requireContext()).goalDao()
            dao.deleteAll()
            println("buraya igriş yaptı")
        }

    }

    private fun deletebyId(id:Int){
        launch {
            val dao=ManagDataBase(requireContext()).goalDao()
            dao.deleteById(id)
            println("buraya igriş yaptı")
        }

    }

}