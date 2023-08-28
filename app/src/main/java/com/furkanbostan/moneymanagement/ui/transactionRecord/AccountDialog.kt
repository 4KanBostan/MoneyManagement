package com.furkanbostan.moneymanagement.ui.transactionRecord

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.database.Account
import com.furkanbostan.moneymanagement.database.Category
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.FragmentAccountDialogBinding
import com.furkanbostan.moneymanagement.ui.transactionRecord.adapter.AccountCardAdapter
import com.furkanbostan.moneymanagement.ui.transactionRecord.adapter.CategoryCardAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AccountDialog(private val itemClickListener: (Account) -> Unit) : BottomSheetDialogFragment(),CoroutineScope {
    private lateinit var binding: FragmentAccountDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountDialogBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllAccount()
    }

    private fun getAllAccount() {
        launch {
            val dao= ManagDataBase(requireContext()).accountDao()
            setRcv(dao.getAllAccount() as ArrayList<Account>)
        }
    }

    private fun setRcv(accounts: ArrayList<Account>) {
        binding.accountsRecycler.apply {
            layoutManager= GridLayoutManager(requireContext(),2)
            adapter = AccountCardAdapter(requireContext(),accounts){ accountItem ->
                dismiss()
                itemClickListener(accountItem)
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

    interface OnItemClickListenerExpense {
        fun onItemClick(item: Account)
    }


}