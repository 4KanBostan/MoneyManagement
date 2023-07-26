package com.furkanbostan.moneymanagement.ui.reportPage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.databinding.ItemReportCategoryBinding
import com.furkanbostan.moneymanagement.model.ReportChartCategory

class PieChartAdapter(val itemArray:ArrayList<ReportChartCategory>):RecyclerView.Adapter<PieChartAdapter.PieChartAdapterViewHolder>() {
    class PieChartAdapterViewHolder(itemBinding:ItemReportCategoryBinding):RecyclerView.ViewHolder(itemBinding.root) {
        val binding:ItemReportCategoryBinding=itemBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PieChartAdapterViewHolder {
        return PieChartAdapterViewHolder(ItemReportCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return itemArray.size
    }

    override fun onBindViewHolder(holder: PieChartAdapterViewHolder, position: Int) {

        val temp = itemArray[position]

        holder.binding.rate.text=temp.rate.toString()
        holder.binding.rcvCategoryName.text=temp.name
        holder.binding.rcvCategoryAmount.text=temp.amount.toString()
    }
}