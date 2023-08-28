package com.furkanbostan.moneymanagement.ui.homePage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.database.Transactions
import com.furkanbostan.moneymanagement.database.TransactionsWithCategoryAndAccount
import com.furkanbostan.moneymanagement.databinding.ItemHomeTransBinding

class HomeTransAdapter(val context:Context, val itemArray:ArrayList<TransactionsWithCategoryAndAccount>):RecyclerView.Adapter<HomeTransAdapter.HomeTransAdapetrViewHolder>() {
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
        holder.binding.accountHomeTv.text = temp.firstAccount.name
        holder.binding.commentHomeTv.text = temp.transaction.note
        holder.binding.amountHomeTv.text = temp.transaction.amount.toString()
        Glide.with(context).load(temp.category.image_url).into(holder.binding.categoryImage)
    }
}