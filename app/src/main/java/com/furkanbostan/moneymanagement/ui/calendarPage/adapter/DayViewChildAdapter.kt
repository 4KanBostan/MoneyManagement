package com.furkanbostan.moneymanagement.ui.calendarPage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkanbostan.moneymanagement.database.Transactions
import com.furkanbostan.moneymanagement.database.TransactionsWithCategoryAndAccount
import com.furkanbostan.moneymanagement.databinding.ItemDialogBinding

class DayViewChildAdapter(val context:Context, val transactionList:ArrayList<TransactionsWithCategoryAndAccount>):RecyclerView.Adapter<DayViewChildAdapter.DayViewChildAdapterViewHolder>() {
    class DayViewChildAdapterViewHolder(itemBinding:ItemDialogBinding):RecyclerView.ViewHolder(itemBinding.root) {
        val binding: ItemDialogBinding=itemBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewChildAdapterViewHolder {
        return DayViewChildAdapterViewHolder(ItemDialogBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: DayViewChildAdapterViewHolder, position: Int) {
        val temp = transactionList[position]
        holder.binding.accountTv.text = temp.account.name
        holder.binding.commentTv.text = temp.transaction.note
        holder.binding.amountTv.text = temp.transaction.amount.toString()
        Glide.with(context).load(temp.category.image_url).into(holder.binding.categoryImageView)
    }
}