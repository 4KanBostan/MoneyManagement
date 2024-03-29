package com.furkanbostan.moneymanagement.ui.calendarPage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.database.TransactionsWithCategoryAndAccount
import com.furkanbostan.moneymanagement.databinding.ItemParentRecyclerBinding
import com.furkanbostan.moneymanagement.model.ParentRecycleView

class DayViewParentAdapter(val itemList:ArrayList<ParentRecycleView>): RecyclerView.Adapter<DayViewParentAdapter.DayViewParentAdapterViewHolder>() {
    inner class DayViewParentAdapterViewHolder(itemBinding:ItemParentRecyclerBinding):RecyclerView.ViewHolder(itemBinding.root) {

        val binding: ItemParentRecyclerBinding=itemBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewParentAdapterViewHolder {
        return DayViewParentAdapterViewHolder(ItemParentRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DayViewParentAdapterViewHolder, position: Int) {
        var incomeCount=0f
        var expenseCount=0f
        val item = itemList[position]
        holder.binding.dateTv.text= item.date

        for(trans in item.itemList){
            if (trans.transaction.type!==3){
                if(trans.transaction.type==0) incomeCount+=trans.transaction.amount
                else expenseCount+= trans.transaction.amount
            }
        }

        holder.binding.incomeTv.text=incomeCount.toInt().toString()
        holder.binding.expenseTv.text= expenseCount.toInt().toString()

        holder.binding.childReyclerView.apply {
            layoutManager = LinearLayoutManager(holder.binding.childReyclerView.context,RecyclerView.VERTICAL,false)
            adapter = DayViewChildAdapter(context,item.itemList)
        }
        holder.binding.dateTv.setOnClickListener {
            holder.binding.childReyclerView.visibility = if (holder.binding.childReyclerView.isShown) View.GONE else View.VISIBLE
        }
    }

    fun updateData(newTransactions: List<ParentRecycleView>) {
        itemList.clear()
        itemList.addAll(newTransactions)
        notifyDataSetChanged()
    }

}