package com.furkanbostan.moneymanagement.ui.calendarPage

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.database.Transactions
import com.furkanbostan.moneymanagement.database.TransactionsWithCategoryAndAccount
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.FragmentDayViewCalendarBinding
import com.furkanbostan.moneymanagement.model.ParentRecycleView
import com.furkanbostan.moneymanagement.ui.BaseFragment
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class DayViewCalendarFragment : BaseFragment() {
    private lateinit var binding: FragmentDayViewCalendarBinding
    private lateinit var transactionsList: ArrayList<TransactionsWithCategoryAndAccount>
    private lateinit var parentRecyclerView: RecyclerView
    private lateinit var transactionList: ArrayList<Transactions>
    private lateinit var parentList: ArrayList<ParentRecycleView>
    private lateinit var inputDateOfBirth: String
    private val today = LocalDate.now()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDayViewCalendarBinding.inflate(layoutInflater, container, false)
    
        /*parentRecyclerView = binding.parentRecylerView
        parentRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter = DayViewParentAdapter(ornekTrans())
        }*/
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        binding.linearLayoutDayview.setOnClickListener {
            createDialogWithoutDateField()?.show()
        }


    }


    /*  fun ornekTrans():List<ParentRecycleView>{
        val t = T("Eğlence","Banka Kartı",3500f,"22/3/2023","Oyun makinesi",R.drawable.cutlery)
        val t1 = Transaction("Eğlence","Banka Kartı",3500f,"22/3/2023","Oyun makinesi",R.drawable.cutlery)
        val t2 = Transaction("Eğlence","Banka Kartı",3500f,"22/3/2023","Oyun makinesi",R.drawable.cutlery)
        transactionList=ArrayList()
        transactionList.add(t)
        transactionList.add(t1)
        transactionList.add(t2)
        val date:LocalDate = LocalDate.now()
        val p = ParentRecycleView(date.minusDays(2),transactionList)
        val p1 = ParentRecycleView(date.plusDays(1),transactionList)
        val p2 = ParentRecycleView(date.plusDays(2),transactionList)
        val p3 = ParentRecycleView(date.plusDays(3),transactionList)
        parentList=ArrayList()
        parentList.add(p)
        parentList.add(p1)
        parentList.add(p2)
        parentList.add(p3)

        return parentList
    }
*/

    private fun getTransOfMonth(month: String) {
        transactionList = ArrayList()
        launch {
            val dao = ManagDataBase(requireContext()).transactionsDao()
            transactionsList =
                dao.getTransactionsWithCategoryAndAccountofMounth(month) as ArrayList<TransactionsWithCategoryAndAccount>
        }
    }

    private fun createDialogWithoutDateField(): DatePickerDialog? {
        val dpd = DatePickerDialog(requireContext(), null, 2014, 1, 24)
        try {
            val datePickerDialogFields = dpd.javaClass.declaredFields
            for (datePickerDialogField in datePickerDialogFields) {
                if (datePickerDialogField.name == "mDatePicker") {
                    datePickerDialogField.isAccessible = true
                    val datePicker = datePickerDialogField[dpd] as DatePicker
                    val datePickerFields = datePickerDialogField.type.declaredFields
                    for (datePickerField in datePickerFields) {
                        Log.i("test", datePickerField.name)
                        if ("mDaySpinner" == datePickerField.name) {
                            datePickerField.isAccessible = true
                            val dayPicker = datePickerField[datePicker]
                            (dayPicker as View).visibility = View.GONE
                        }
                    }
                }
            }
        } catch (ex: Exception) {
        }
        return dpd
    }
}