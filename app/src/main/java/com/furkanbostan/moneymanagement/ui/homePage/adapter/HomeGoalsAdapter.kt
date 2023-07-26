package com.furkanbostan.moneymanagement.ui.homePage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkanbostan.moneymanagement.database.GoalAndCategory
import com.furkanbostan.moneymanagement.databinding.ItemHomeGoalsBinding

class HomeGoalsAdapter(val context:Context, val itemArray:ArrayList<GoalAndCategory>):RecyclerView.Adapter<HomeGoalsAdapter.HomeGoalsAdapterViewHolder>() {
    class HomeGoalsAdapterViewHolder(val itemBinding:ItemHomeGoalsBinding):RecyclerView.ViewHolder(itemBinding.root) {
        val binding:ItemHomeGoalsBinding=itemBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeGoalsAdapterViewHolder {
        return HomeGoalsAdapterViewHolder(ItemHomeGoalsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return itemArray.size
    }

    override fun onBindViewHolder(holder: HomeGoalsAdapterViewHolder, position: Int) {
        val temp = itemArray.get(position)

        holder.binding.homeGoalsName.text=temp.goal.description
        holder.binding.homeGoalsAmount.text=temp.goal.amount.toInt().toString()
        holder.binding.homeGoalsBalance.text=temp.goal.target_goal.toInt().toString()
        val progCount= (temp.goal.amount)/(temp.goal.target_goal)*100
        holder.binding.progressBar.apply {
            progress=progCount.toInt()
            max=100}
        Glide.with(context).load(temp.category.image_url).into(holder.binding.homeGoalsImage)

    }

}