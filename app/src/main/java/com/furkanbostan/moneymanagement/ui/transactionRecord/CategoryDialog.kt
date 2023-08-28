package com.furkanbostan.moneymanagement.ui.transactionRecord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.furkanbostan.moneymanagement.database.Account
import com.furkanbostan.moneymanagement.database.Category
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.FragmentCategoryDialogBinding
import com.furkanbostan.moneymanagement.ui.transactionRecord.adapter.CategoryCardAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CategoryDialog(private val type:Int,private val itemClickListener: (Category) -> Unit) : BottomSheetDialogFragment(),CoroutineScope {
    private lateinit var binding:FragmentCategoryDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoryDialogBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (type==0)getAllExpenseCategory()
        else getAllIncomeCategory()

    }

    private fun getAllExpenseCategory() {
        launch {
            val dao= ManagDataBase(requireContext()).categoryDao()
            setRcv(dao.getAllExpenseCategory() as ArrayList<Category>)
        }
    }
    private fun getAllIncomeCategory() {
        launch {
            val dao= ManagDataBase(requireContext()).categoryDao()
            setRcv(dao.getAllIncomeCategory() as ArrayList<Category>)
        }
    }

    private fun setRcv(categories: ArrayList<Category>) {
        binding.categoryRecycler.apply {
            layoutManager= GridLayoutManager(requireContext(),2)
            adapter = CategoryCardAdapter(requireContext(),categories){ categoryItem ->
                dismiss()
                itemClickListener(categoryItem)
            }
        }

    }


    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    interface OnItemClickListenerIncome {
        fun onItemClick(item: Category)
    }
}