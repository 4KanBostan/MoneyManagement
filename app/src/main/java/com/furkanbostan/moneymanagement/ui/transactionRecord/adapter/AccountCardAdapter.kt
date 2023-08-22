package com.furkanbostan.moneymanagement.ui.transactionRecord.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.database.Account
import com.furkanbostan.moneymanagement.database.Category
import com.furkanbostan.moneymanagement.databinding.ItemGoalCardBinding
import com.furkanbostan.moneymanagement.ui.transactionRecord.AccountDialog
import com.furkanbostan.moneymanagement.ui.transactionRecord.CategoryDialog

class AccountCardAdapter (val context: Context, val itemList:ArrayList<Account>, val itemClickListener: AccountDialog.OnItemClickListenerExpense): RecyclerView.Adapter<AccountCardAdapter.AccountCardAdapterViewHolder>() {
    class AccountCardAdapterViewHolder(itemBinding: ItemGoalCardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val binding = itemBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountCardAdapterViewHolder {
        return AccountCardAdapterViewHolder(ItemGoalCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: AccountCardAdapterViewHolder, position: Int) {
        val temp = itemList[position]

        holder.binding.cardGoalName.text = temp.name
        Glide.with(context).load(R.drawable.banking_card).into(holder.binding.goalCardImg)
        holder.binding.goalCardItemLayout.setOnClickListener {
            itemClickListener.onItemClick(temp)
        }
    }
}