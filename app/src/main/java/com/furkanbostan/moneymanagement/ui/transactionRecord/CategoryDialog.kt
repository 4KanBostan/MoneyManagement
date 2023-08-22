package com.furkanbostan.moneymanagement.ui.transactionRecord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
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

class CategoryDialog : BottomSheetDialogFragment(),CoroutineScope {
    private lateinit var binding:FragmentCategoryDialogBinding
    private lateinit var categoryArray:ArrayList<Category>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoryDialogBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllCategory()
    }

    private fun getAllCategory() {
        launch {
            val dao= ManagDataBase(requireContext()).categoryDao()
            setRcv(dao.getAllCategory() as ArrayList<Category>)
        }
    }

    private fun setRcv(categories: ArrayList<Category>) {
        binding.categoryRecycler.apply {
            layoutManager= GridLayoutManager(requireContext(),2)
            adapter = CategoryCardAdapter(requireContext(),categories, object: OnItemClickListenerIncome {
                override fun onItemClick(item: Category) {
                    val mainFragment = parentFragment as? IncomeRecordFragment
                    mainFragment?.updateIncomeCategoryEt(item)
                    dismiss()
                }
            })
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