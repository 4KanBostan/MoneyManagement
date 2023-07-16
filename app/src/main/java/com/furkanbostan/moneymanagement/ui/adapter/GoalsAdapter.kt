package com.furkanbostan.moneymanagement.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkanbostan.moneymanagement.databinding.ItemGoalsBinding
import com.furkanbostan.moneymanagement.model.Goals

class GoalsAdapter(val context:Context, val itemArray:ArrayList<Goals>):RecyclerView.Adapter<GoalsAdapter.GoalsAdapterViewHolder>() {
    class GoalsAdapterViewHolder(val itemBinding:ItemGoalsBinding):RecyclerView.ViewHolder(itemBinding.root) {
        val binding:ItemGoalsBinding=itemBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalsAdapterViewHolder {
        return GoalsAdapterViewHolder(ItemGoalsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return itemArray.size
    }

    override fun onBindViewHolder(holder: GoalsAdapterViewHolder, position: Int) {
        val temp= itemArray[position]

        holder.binding.goalsName.text=temp.name
        holder.binding.goalsDate.text=temp.date.toString()
        holder.binding.goalsBalance.text=temp.balance.toString()
        holder.binding.goalsAmount.text=temp.amount.toString()
        Glide.with(context).load(temp.image).into(holder.binding.goalsImage)
        holder.binding.horizontalProgressBar.apply {
            progress=temp.progCount
            max=100
        }
    }
}