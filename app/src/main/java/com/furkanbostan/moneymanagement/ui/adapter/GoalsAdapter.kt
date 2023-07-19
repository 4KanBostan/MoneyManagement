package com.furkanbostan.moneymanagement.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkanbostan.moneymanagement.database.Goal
import com.furkanbostan.moneymanagement.databinding.ItemGoalsBinding
import com.furkanbostan.moneymanagement.model.Goals

class GoalsAdapter(val context:Context, val itemArray:ArrayList<Goal>):RecyclerView.Adapter<GoalsAdapter.GoalsAdapterViewHolder>() {
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
        holder.binding.goalsDate.text=temp.target_date
        holder.binding.goalsBalance.text=temp.amount.toInt().toString()
        holder.binding.goalsTarget.text=temp.target_goal.toInt().toString()
        Glide.with(context).load(temp.image_url).into(holder.binding.goalsImage)
        holder.binding.horizontalProgressBar.apply {
            progress= temp.amount.toInt()
            max=100
        }
    }
}