package com.furkanbostan.moneymanagement.ui.transactionRecord

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.databinding.FragmentExpenseRecordBinding
import java.util.*

class ExpenseRecordFragment : Fragment() {
    private lateinit var binding:FragmentExpenseRecordBinding
    private var transCategoryId = 0
    private var transAccountId = 0
    private var transAmount = 0f
    private var transDate = ""
    private var transNote = ""
    private var transDateDay =""
    private var transDateMonth=""
    private var transDateYear=""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentExpenseRecordBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.expenseDateTextInputEditText.setOnClickListener {
            datepicker()
        }
        binding.expenseDateTextInputEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                datepicker()
            }
        }
    }



    private fun datepicker(){
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                binding.expenseDateTextInputEditText.setText(dat)
                splitDate(dat)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }


    private fun splitDate(date:String) {
        val dateSplit = date.split("-")
        transDateDay= "%02d".format(dateSplit[0].toInt())
        transDateMonth = "%02d".format(dateSplit[1].toInt())
        transDateYear = "%04d".format(dateSplit[2].toInt())
        transDate = "$transDateDay-$transDateMonth-$transDateYear"
    }
}