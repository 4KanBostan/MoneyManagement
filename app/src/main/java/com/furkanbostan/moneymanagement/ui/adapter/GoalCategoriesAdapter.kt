package com.furkanbostan.moneymanagement.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkanbostan.moneymanagement.database.GoalCategory
import com.furkanbostan.moneymanagement.databinding.ItemGoalCardBinding
import com.furkanbostan.moneymanagement.databinding.ItemGoalCategoriesBinding

class GoalCategoriesAdapter(val context:Context, val arrayList: ArrayList<GoalCategory>):RecyclerView.Adapter<GoalCategoriesAdapter.GoalCategoriesAdapterViewHolder>() {
    class GoalCategoriesAdapterViewHolder(itembinding:ItemGoalCategoriesBinding):RecyclerView.ViewHolder(itembinding.root) {
        val binding:ItemGoalCategoriesBinding=itembinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalCategoriesAdapterViewHolder {
        return GoalCategoriesAdapterViewHolder(ItemGoalCategoriesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: GoalCategoriesAdapterViewHolder, position: Int) {
        val temp = arrayList.get(position)
        holder.binding.goalCategoryName.text=temp.name
        Glide.with(context).load(temp.image_url).into(holder.binding.goalCategoriesImg)
    }
}