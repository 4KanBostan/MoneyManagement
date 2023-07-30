package com.furkanbostan.moneymanagement.ui.homePage.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.database.Account
import com.furkanbostan.moneymanagement.databinding.ItemCustomRecyclerBinding

class CustomRecyclerAdapter(val cardArray:ArrayList<Account>):RecyclerView.Adapter<CustomRecyclerAdapter.CustomRecyclerViewViewHolder>() {
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
        holder.binding.totalBalance.text=temp.balance.toString()
        holder.binding.totalIncome.text=temp.income.toString()
        holder.binding.totalExpense.text=temp.expense.toString()
    }
}