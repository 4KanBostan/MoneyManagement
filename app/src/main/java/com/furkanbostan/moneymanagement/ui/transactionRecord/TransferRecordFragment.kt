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
import com.furkanbostan.moneymanagement.databinding.FragmentTransferRecordBinding
import com.furkanbostan.moneymanagement.ui.BaseFragment
import kotlinx.coroutines.launch
import java.util.*

class TransferRecordFragment : BaseFragment() {
    private lateinit var binding:FragmentTransferRecordBinding
    private var transFirstAccountId = 0
    private var transSecondAccountId = 0
    private var transAmount = 0f
    private var transDate = ""
    private var transNote = ""
    private var transDateDay =""
    private var transDateMonth=""
    private var transDateYear=""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding = FragmentTransferRecordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenerEt()

        binding.trasnferFirstAccountTextInputEditText.setOnClickListener{
            val bottomSheetFragment = AccountDialog(){ selectedAccount ->
                binding.trasnferFirstAccountTextInputEditText.setText(selectedAccount.name)
            }
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        binding.transferSecondAccountTextInputEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val bottomSheetFragment = AccountDialog(){ selectedAccount ->
                    binding.transferSecondAccountTextInputEditText.setText(selectedAccount.name)
                }
                bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
            }
        }

        binding.transferSecondAccountTextInputEditText.setOnClickListener {
            val bottomSheetFragment = AccountDialog(){ selectedAccount ->
                binding.transferSecondAccountTextInputEditText.setText(selectedAccount.name)
            }
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        binding.transferDateTextInputEditText.setOnClickListener {
            datepicker()
        }
        binding.transferDateTextInputEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                datepicker()
            }
        }

        binding.transferSaveButton.setOnClickListener {
            saveTransaction()
        }
    }

    private fun listenerEt() {
        binding.trasnferFirstAccountTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrBlank()) {
                    if (binding.transferSecondAccountTextInputEditText.text.isNullOrBlank()){
                        binding.transferSecondAccountTextInputEditText.requestFocus()
                    }
                    if (!binding.trasnferFirstAccountTextInputEditText.text.isNullOrBlank()){
                        getFirstAccountId(binding.trasnferFirstAccountTextInputEditText.text.toString())
                    }
                }
            }
        })

        binding.transferSecondAccountTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrBlank()) {
                    binding.transferAmountTextInputEditText.requestFocus()
                    if (!binding.transferSecondAccountTextInputEditText.text.isNullOrBlank()){
                        getSecondAccountId(binding.transferSecondAccountTextInputEditText.text.toString())
                    }
                }
            }
        })


    }

    private fun saveTransaction() {
        val date = binding.transferDateTextInputEditText.text.toString()
        val category = binding.trasnferFirstAccountTextInputEditText.text.toString()
        val account = binding.transferSecondAccountTextInputEditText.text.toString()
        val amount = binding.transferAmountTextInputEditText.text.toString()
        val note = binding.transferNoteTextInputEditText.text.toString()


        if (category.isNotEmpty() && account.isNotEmpty() && amount.isNotEmpty() &&  date.isNotEmpty()) {

            val model = Transactions(0,null,transFirstAccountId,transSecondAccountId,amount.toFloat(),transDate,note,
                transDateDay,transDateMonth,transDateYear,3)
            insertTransaction(model)
        } else {
            Toast.makeText(requireContext(),"Lütfen tüm alanları doldurunuz!", Toast.LENGTH_LONG).show()
        }

    }

    private fun insertTransaction(model: Transactions){
        launch {
            val dao = ManagDataBase(requireContext()).transactionsDao()
            dao.insert(model)
            Toast.makeText(requireContext(),"İşlem Başarılı", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }


    private fun getFirstAccountId(name:String){
        launch {
            val dao = ManagDataBase(requireContext()).accountDao()
            transFirstAccountId= dao.getCategoryByAccountName(name).id
        }
    }

    private fun getSecondAccountId(name:String){
        launch {
            val dao = ManagDataBase(requireContext()).accountDao()
            transSecondAccountId= dao.getCategoryByAccountName(name).id
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
                binding.transferDateTextInputEditText.setText(dat)
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