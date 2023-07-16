package com.furkanbostan.moneymanagement.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.databinding.ItemParentRecyclerBinding
import com.furkanbostan.moneymanagement.model.ParentRecycleView

class DayViewParentAdapter(val itemList:List<ParentRecycleView>): RecyclerView.Adapter<DayViewParentAdapter.DayViewParentAdapterViewHolder>() {
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
        val item = itemList[position]
        holder.binding.dateTv.text= item.date.toString()
        holder.binding.childReyclerView.apply {
            layoutManager = LinearLayoutManager(holder.binding.childReyclerView.context,RecyclerView.VERTICAL,false)
            adapter = DayViewChildAdapter(context,item.itemList)
        }
        holder.binding.dateTv.setOnClickListener {
            holder.binding.childReyclerView.visibility = if (holder.binding.childReyclerView.isShown) View.GONE else View.VISIBLE
        }
    }
}