package com.furkanbostan.moneymanagement.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkanbostan.moneymanagement.databinding.ItemHomeGoalsBinding
import com.furkanbostan.moneymanagement.model.Goals

class HomeGoalsAdapter(val context:Context, val itemArray:ArrayList<Goals>):RecyclerView.Adapter<HomeGoalsAdapter.HomeGoalsAdapterViewHolder>() {
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

        holder.binding.homeGoalsName.text=temp.name
        holder.binding.homeGoalsAmount.text=temp.amount.toString()
        holder.binding.homeGoalsBalance.text=temp.balance.toString()
        holder.binding.progressBar.apply {
            progress=temp.progCount
            max=100}
        Glide.with(context).load(temp.image).into(holder.binding.homeGoalsImage)

    }

}