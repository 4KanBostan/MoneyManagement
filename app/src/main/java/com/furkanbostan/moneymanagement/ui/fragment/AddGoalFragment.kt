package com.furkanbostan.moneymanagement.ui.fragment

import android.app.DatePickerDialog
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.database.Goal
import com.furkanbostan.moneymanagement.database.GoalCategory
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.FragmentAddGoalBinding
import com.furkanbostan.moneymanagement.ui.BaseFragment
import com.furkanbostan.moneymanagement.ui.DataListener
import com.furkanbostan.moneymanagement.ui.adapter.GoalCardAdapter
import com.furkanbostan.moneymanagement.ui.adapter.GoalsAdapter
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.ArrayList


class AddGoalFragment : BaseFragment() {
   private lateinit var binding:FragmentAddGoalBinding
   private lateinit var itemArray:ArrayList<Goal>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentAddGoalBinding.inflate(layoutInflater,container,false)

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_addGoalFragment_to_goalsFragment)
        }

        binding.targetDateTextInputEdittext.setOnClickListener{
            datepicker()
        }

        binding.goalTextInputEdittext.setOnClickListener{
            val bottomSheetFragment = GoalEditDialog()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)

        }

        binding.saveButton.setOnClickListener {
            saveGoal()
        }
        return binding.root
    }

    private fun saveGoal() {
        val name= binding.goalTextInputEdittext.text.toString()
        val desc= binding.goalDescTextInputEdittext.text.toString()
    }


    fun updateEditText(name:GoalCategory) {
        binding.goalTextInputEdittext.setText(name.name)
    }



    fun datepicker(){
            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    binding.targetDateTextInputEdittext.setText(dat)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
    }

    private fun getAllGoal(){
        var list= listOf<Goal>()
        launch {
            val dao= ManagDataBase(requireContext()).goalDao()
            list=dao.getAllGoals()
            //setRcv(list)
            itemArray=list as ArrayList<Goal>
        }

    }

    private fun insertGoal(goal: Goal){
        launch {
            val dao= ManagDataBase(requireContext()).goalDao()
            dao.insert(goal)
        }

    }


}