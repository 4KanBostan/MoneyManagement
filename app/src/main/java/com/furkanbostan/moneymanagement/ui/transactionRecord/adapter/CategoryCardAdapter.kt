package com.furkanbostan.moneymanagement.ui.transactionRecord.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkanbostan.moneymanagement.database.Category
import com.furkanbostan.moneymanagement.databinding.ItemGoalCardBinding
import com.furkanbostan.moneymanagement.ui.transactionRecord.CategoryDialog

class CategoryCardAdapter (val context: Context, val itemList:ArrayList<Category>, val itemClickListener: CategoryDialog.OnItemClickListenerIncome): RecyclerView.Adapter<CategoryCardAdapter.CategoryCardAdapterViewHolder>() {
    class CategoryCardAdapterViewHolder(itemBinding:ItemGoalCardBinding):RecyclerView.ViewHolder(itemBinding.root){
        val binding= itemBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryCardAdapterViewHolder {
        return CategoryCardAdapterViewHolder(ItemGoalCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CategoryCardAdapterViewHolder, position: Int) {
        val temp = itemList[position]

        holder.binding.cardGoalName.text=temp.name
        Glide.with(context).load(temp.image_url).into(holder.binding.goalCardImg)
        holder.binding.goalCardItemLayout.setOnClickListener {
            itemClickListener.onItemClick(temp)
        }
    }


}