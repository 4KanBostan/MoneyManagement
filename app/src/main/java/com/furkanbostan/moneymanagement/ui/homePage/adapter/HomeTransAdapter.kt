package com.furkanbostan.moneymanagement.ui.homePage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.furkanbostan.moneymanagement.databinding.ItemHomeTransBinding
import com.furkanbostan.moneymanagement.model.Transaction

class HomeTransAdapter(val context:Context, val itemArray:ArrayList<Transaction>):RecyclerView.Adapter<HomeTransAdapter.HomeTransAdapetrViewHolder>() {
    class HomeTransAdapetrViewHolder(val itembinding:ItemHomeTransBinding):ViewHolder(itembinding.root) {
        val binding:ItemHomeTransBinding=itembinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTransAdapetrViewHolder {
        return HomeTransAdapetrViewHolder(ItemHomeTransBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return itemArray.size
    }

    override fun onBindViewHolder(holder: HomeTransAdapetrViewHolder, position: Int) {
        val temp = itemArray[position]
        holder.binding.accountHomeTv.text = temp.account
        holder.binding.commentHomeTv.text = temp.comment
        holder.binding.amountHomeTv.text = temp.amount.toString()
        Glide.with(context).load(temp.image).into(holder.binding.categoryImage)
    }
}