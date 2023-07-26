package com.furkanbostan.moneymanagement.ui.goalsPage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkanbostan.moneymanagement.database.GoalCategory
import com.furkanbostan.moneymanagement.databinding.ItemGoalCardBinding
import com.furkanbostan.moneymanagement.ui.goalsPage.GoalEditDialog

class GoalCardAdapter(val context: Context, val itemList:ArrayList<GoalCategory>, val itemClickListener: GoalEditDialog.OnItemClickListener):RecyclerView.Adapter<GoalCardAdapter.GoalCardAdapterViewHolder>(){

     class GoalCardAdapterViewHolder(itemBinding: ItemGoalCardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val binding = itemBinding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalCardAdapterViewHolder {
        return GoalCardAdapterViewHolder(
            ItemGoalCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: GoalCardAdapterViewHolder, position: Int) {
        val temp = itemList.get(position)

        holder.binding.cardGoalName.text = temp.name
        Glide.with(context).load(temp.image_url).into(holder.binding.goalCardImg)
        holder.binding.goalCardItemLayout.setOnClickListener {
            itemClickListener.onItemClick(temp)
        }
    }


}