package com.furkanbostan.moneymanagement.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkanbostan.moneymanagement.database.Goal
import com.furkanbostan.moneymanagement.database.GoalAndCategory
import com.furkanbostan.moneymanagement.databinding.ItemGoalsBinding
import com.furkanbostan.moneymanagement.model.Goals

class GoalsAdapter(val context:Context, val itemArray:ArrayList<GoalAndCategory>):RecyclerView.Adapter<GoalsAdapter.GoalsAdapterViewHolder>() {
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

        holder.binding.goalsName.text=temp.goal.description
        holder.binding.goalsDate.text=temp.goal.target_date
        holder.binding.goalsBalance.text=temp.goal.amount.toInt().toString()
        holder.binding.goalsTarget.text=temp.goal.target_goal.toInt().toString()
        Glide.with(context).load(temp.category.image_url).into(holder.binding.goalsImage)
        holder.binding.horizontalProgressBar.apply {
            progress= temp.goal.amount.toInt()
            max=100
        }
    }
}