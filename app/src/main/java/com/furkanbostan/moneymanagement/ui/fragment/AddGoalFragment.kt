package com.furkanbostan.moneymanagement.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.databinding.FragmentAddGoalBinding
import java.util.*


class AddGoalFragment : Fragment() {
   private lateinit var binding:FragmentAddGoalBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentAddGoalBinding.inflate(layoutInflater,container,false)

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_addGoalFragment_to_goalsFragment)
        }

        binding.targetDateTextInputEdittext.setOnClickListener{
            datepicker()
        }
        return binding.root
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

}