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
import com.furkanbostan.moneymanagement.database.Account
import com.furkanbostan.moneymanagement.database.Category
import com.furkanbostan.moneymanagement.database.Transactions
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.FragmentIncomeRecordBinding
import com.furkanbostan.moneymanagement.ui.BaseFragment
import kotlinx.coroutines.launch
import java.util.*


class IncomeRecordFragment : BaseFragment() {
    private lateinit var binding: FragmentIncomeRecordBinding
    private var transCategoryId = 0
    private var transAccountId = 0
    private var transAmount = 0f
    private var transDate = ""
    private var transNote = ""
    private var transDateDay =""
    private var transDateMonth=""
    private var transDateYear=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentIncomeRecordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenerEt()

        binding.incomeCategoryTextInputEditText.setOnClickListener{
            val bottomSheetFragment = CategoryDialog()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }

        binding.incomeAccountTextInputEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val bottomSheetFragment = AccountDialog()
                bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
            }
        }

        binding.incomeAccountTextInputEditText.setOnClickListener {
            val bottomSheetFragment = AccountDialog()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }

        binding.incomeDateTextInputEditText.setOnClickListener {
            datepicker()
        }
        binding.incomeDateTextInputEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
               datepicker()
            }
        }

        binding.incomeSaveButton.setOnClickListener {
            saveTransaction()
        }


    }

    private fun saveTransaction() {
        val date = binding.incomeDateTextInputEditText.text.toString()
        val category = binding.incomeCategoryTextInputEditText.text.toString()
        val account = binding.incomeAccountTextInputEditText.text.toString()
        val amount = binding.incomeAmountTextInputEditText.text.toString()
        val note = binding.incomeNoteTextInputEditText.text.toString()


        if (category.isNotEmpty() && account.isNotEmpty() && amount.isNotEmpty() &&  date.isNotEmpty()) {

            val model = Transactions(0,transCategoryId,transAccountId,amount.toFloat(),transDate,note,
                                            transDateDay,transDateMonth,transDateYear,true)
            insertTransaction(model)
        } else {
            Toast.makeText(requireContext(),"Lütfen tüm alanları doldurunuz!",Toast.LENGTH_LONG).show()
        }

    }

    private fun splitDate(date:String) {
        val dateSplit = date.split("-")
        transDateDay= "%02d".format(dateSplit[0].toInt())
        transDateMonth = "%02d".format(dateSplit[1].toInt())
        transDateYear = "%04d".format(dateSplit[2].toInt())
        transDate = "$transDateDay-$transDateMonth-$transDateYear"
    }

    private fun listenerEt() {
        binding.incomeCategoryTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrBlank()) {
                    if (binding.incomeAccountTextInputEditText.text.isNullOrBlank()){
                        binding.incomeAccountTextInputEditText.requestFocus()
                    }
                    if (!binding.incomeCategoryTextInputEditText.text.isNullOrBlank()){
                        getCategoryId(binding.incomeCategoryTextInputEditText.text.toString())
                    }
                }
            }
        })

        binding.incomeAccountTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrBlank()) {
                    binding.incomeAmountTextInputEditText.requestFocus()
                    if (!binding.incomeAccountTextInputEditText.text.isNullOrBlank()){
                        getAccountId(binding.incomeAccountTextInputEditText.text.toString())
                    }
                }
            }
        })


    }


    fun updateIncomeCategoryEt(item: Category) {
        binding.incomeCategoryTextInputEditText.setText(item.name)
    }
    fun updateIncomeAccountEt(item: Account) {
        binding.incomeAccountTextInputEditText.setText(item.name)
    }

    private fun datepicker(){
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                binding.incomeDateTextInputEditText.setText(dat)
                splitDate(dat)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
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

    private fun insertTransaction(model:Transactions){
        launch {
            val dao = ManagDataBase(requireContext()).transactionsDao()
            dao.insert(model)
            Toast.makeText(requireContext(),"İşlem Başarılı",Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

}