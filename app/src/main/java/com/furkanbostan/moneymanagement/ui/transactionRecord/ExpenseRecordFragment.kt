package com.furkanbostan.moneymanagement.ui.transactionRecord

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.database.Transactions
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.FragmentExpenseRecordBinding
import com.furkanbostan.moneymanagement.ui.BaseFragment
import kotlinx.coroutines.launch
import java.util.*

class ExpenseRecordFragment : BaseFragment() {
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

        listenerEt()

        binding.expenseCategoryTextInputEditText.setOnClickListener{
            val bottomSheetFragment = CategoryDialog(0){ selectedAccount ->
                binding.expenseCategoryTextInputEditText.setText(selectedAccount.name)
            }
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        binding.expenseAccountTextInputEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val bottomSheetFragment = AccountDialog(){ selectedAccount ->
                    binding.expenseAccountTextInputEditText.setText(selectedAccount.name)
                }
                bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
            }
        }

        binding.expenseAccountTextInputEditText.setOnClickListener {
            val bottomSheetFragment = AccountDialog(){ selectedAccount ->
                binding.expenseAccountTextInputEditText.setText(selectedAccount.name)
            }
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }

        binding.expenseDateTextInputEditText.setOnClickListener {
            datepicker()
        }
        binding.expenseDateTextInputEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                datepicker()
            }
        }

        binding.expenseSaveButton.setOnClickListener {
            saveTransaction()
        }
    }
    private fun listenerEt() {
        binding.expenseCategoryTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrBlank()) {
                    if (binding.expenseAccountTextInputEditText.text.isNullOrBlank()){
                        binding.expenseAccountTextInputEditText.requestFocus()
                    }
                    if (!binding.expenseCategoryTextInputEditText.text.isNullOrBlank()){
                        getCategoryId(binding.expenseCategoryTextInputEditText.text.toString())
                    }
                }
            }
        })

        binding.expenseAccountTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrBlank()) {
                    binding.expenseAmountTextInputEditText.requestFocus()
                    if (!binding.expenseAccountTextInputEditText.text.isNullOrBlank()){
                        getAccountId(binding.expenseAccountTextInputEditText.text.toString())
                    }
                }
            }
        })


    }
    private fun saveTransaction() {
        val date = binding.expenseDateTextInputEditText.text.toString()
        val category = binding.expenseCategoryTextInputEditText.text.toString()
        val account = binding.expenseAccountTextInputEditText.text.toString()
        val amount = binding.expenseAmountTextInputEditText.text.toString()
        val note = binding.expenseNoteTextInputEditText.text.toString()


        if (category.isNotEmpty() && account.isNotEmpty() && amount.isNotEmpty() &&  date.isNotEmpty()) {

            val model = Transactions(0,transCategoryId,transAccountId,null,amount.toFloat(),transDate,note,
                transDateDay,transDateMonth,transDateYear,1)
            insertTransaction(model)
        } else {
            Toast.makeText(requireContext(),"Lütfen tüm alanları doldurunuz!", Toast.LENGTH_LONG).show()
        }

    }

    private fun insertTransaction(model:Transactions){
        launch {
            val dao = ManagDataBase(requireContext()).transactionsDao()
            dao.insert(model)
            Toast.makeText(requireContext(),"İşlem Başarılı",Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun getCategoryId(name:String){
        launch {
            val dao = ManagDataBase(requireContext()).categoryDao()
            transCategoryId= dao.getCategoryByCtegoryName(name).id
        }
    }

    private fun getAccountId(name:String){
        launch {
            val dao = ManagDataBase(requireContext()).accountDao()
            transAccountId= dao.getCategoryByAccountName(name).id
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