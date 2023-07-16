package com.furkanbostan.moneymanagement.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkanbostan.moneymanagement.databinding.ItemDialogBinding
import com.furkanbostan.moneymanagement.model.Transaction

class DayViewChildAdapter(val context:Context, val transactionList:ArrayList<Transaction>):RecyclerView.Adapter<DayViewChildAdapter.DayViewChildAdapterViewHolder>() {
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
        holder.binding.accountTv.text = temp.account
        holder.binding.commentTv.text = temp.comment
        holder.binding.amountTv.text = temp.amount.toString()
        Glide.with(context).load(temp.image).into(holder.binding.categoryImageView)
    }
}