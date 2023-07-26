package com.furkanbostan.moneymanagement.ui.goalsPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.database.Goal
import com.furkanbostan.moneymanagement.database.GoalAndCategory
import com.furkanbostan.moneymanagement.database.GoalCategory
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.FragmentGoalsBinding
import com.furkanbostan.moneymanagement.ui.BaseFragment
import com.furkanbostan.moneymanagement.ui.goalsPage.adapter.GoalsAdapter
import kotlinx.coroutines.launch
import java.time.LocalDate


class GoalsFragment : BaseFragment() {
        private lateinit var binding:FragmentGoalsBinding
        private lateinit var goalsList:ArrayList<Goal>
        private lateinit var localDate: LocalDate
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentGoalsBinding.inflate(layoutInflater,container,false)
        storeInRoom()
        addCategory()
        addGoal()
        totalSaving()



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


    fun setRcv( list: List<GoalAndCategory>){
        val liste:ArrayList<GoalAndCategory> =list as ArrayList<GoalAndCategory>
        binding.goalsRecycler.apply {
            layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter= GoalsAdapter(context,liste)
        }
    }

    fun addGoal():ArrayList<Goal>{
        localDate= LocalDate.now()



        goalsList= ArrayList()
        goalsList.add(Goal(0,"Araba",70f,2500f,localDate.toString(),localDate.dayOfMonth.toString(),localDate.month.toString(),localDate.year.toString(),15))
        goalsList.add(Goal(0,"Araba",50f,2500f,localDate.toString(),localDate.dayOfMonth.toString(),localDate.month.toString(),localDate.year.toString(),15))
        goalsList.add(Goal(0,"Araba",30f,2500f,localDate.toString(),localDate.dayOfMonth.toString(),localDate.month.toString(),localDate.year.toString(),15))
        goalsList.add(Goal(0,"Araba",20f,2500f,localDate.toString(),localDate.dayOfMonth.toString(),localDate.month.toString(),localDate.year.toString(),15))
        storeAllInRoom(goalsList)

        return goalsList
    }

    private fun storeInRoom(){
        localDate= LocalDate.now()
        launch {
            val dao=ManagDataBase(requireContext()).goalDao()
            dao.insert(Goal(0,"Araba",50f,2500f,localDate.toString(),localDate.dayOfMonth.toString(),localDate.month.toString(),localDate.year.toString(),15))
            println("buraya igriş yaptı")
        }
    }
    private fun storeAllInRoom(list: List<Goal>){
        launch {
            val dao=ManagDataBase(requireContext()).goalDao()
            dao.insertAll(*list.toTypedArray())
        }
    }

    private fun getAllGoal(){
        var list= listOf<GoalAndCategory>()
        launch {
            val dao=ManagDataBase(requireContext()).goalDao()
            list=dao.getGoalAndCategory()
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

    private fun addCategory(){
        val goalCategory= GoalCategory(0,"Araba",R.drawable.car)
        launch {
            val dao= ManagDataBase(requireContext()).goalCategoryDao()
            dao.insert(goalCategory)
        }
    }

    private fun getDeleteCategory(){
        launch {
            val dao= ManagDataBase(requireContext()).goalCategoryDao()
            dao.deleteAll()

        }
    }




}