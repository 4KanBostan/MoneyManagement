package com.furkanbostan.moneymanagement.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.databinding.ItemCustomRecyclerBinding
import com.furkanbostan.moneymanagement.model.HomeAccount

class CustomRecyclerAdapter(val cardArray:ArrayList<HomeAccount>):RecyclerView.Adapter<CustomRecyclerAdapter.CustomRecyclerViewViewHolder>() {
    class CustomRecyclerViewViewHolder(itembinding:ItemCustomRecyclerBinding):RecyclerView.ViewHolder(itembinding.root) {
        val binding:ItemCustomRecyclerBinding=itembinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomRecyclerViewViewHolder {
       return CustomRecyclerViewViewHolder(ItemCustomRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return cardArray.size
    }

    override fun onBindViewHolder(holder: CustomRecyclerViewViewHolder, position: Int) {
        val temp = cardArray[position]

        holder.binding.homeAccountName.text= temp.name
        holder.binding.totalBalance.text=temp.totalBalance.toString()
        holder.binding.totalIncome.text=temp.totalIncome.toString()
        holder.binding.totalExpense.text=temp.totalExpense.toString()
    }
}