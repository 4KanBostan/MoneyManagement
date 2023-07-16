package com.furkanbostan.moneymanagement.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.databinding.ItemMonthBinding
import com.furkanbostan.moneymanagement.model.MonthTransaction

class MonthViewAdapter(val monthList: ArrayList<MonthTransaction>):RecyclerView.Adapter<MonthViewAdapter.MonthViewAdapterViewHolder>() {

    class MonthViewAdapterViewHolder(itemBinding: ItemMonthBinding):RecyclerView.ViewHolder(itemBinding.root) {
        val binding: ItemMonthBinding= itemBinding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewAdapterViewHolder {
        return MonthViewAdapterViewHolder(ItemMonthBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return monthList.size
    }

    override fun onBindViewHolder(holder: MonthViewAdapterViewHolder, position: Int) {
        val temp= monthList[position]

        holder.binding.incomeTv.text= temp.income.toString()
        holder.binding.expenseTv.text= temp.expense.toString()
        holder.binding.totalTv.text= temp.total.toString()
        holder.binding.nameTv.text= temp.name

    }
}