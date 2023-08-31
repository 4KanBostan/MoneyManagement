package com.furkanbostan.moneymanagement.ui.calendarPage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.database.Transactions
import com.furkanbostan.moneymanagement.database.TransactionsWithCategoryAndAccount
import com.furkanbostan.moneymanagement.databinding.ItemDialogBinding
import com.furkanbostan.moneymanagement.util.setTextColorRes

class DailyTransactionsAdapter(val context:Context,var transactionList: ArrayList<TransactionsWithCategoryAndAccount>): RecyclerView.Adapter<DailyTransactionsAdapter.DailyTransactionsAdapterViewHolder>(){


    inner class DailyTransactionsAdapterViewHolder(itemBinding:ItemDialogBinding):RecyclerView.ViewHolder(itemBinding.root) {
        val binding:ItemDialogBinding = itemBinding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyTransactionsAdapterViewHolder {

        return DailyTransactionsAdapterViewHolder(ItemDialogBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: DailyTransactionsAdapterViewHolder, position: Int) {
        val temp = transactionList[position]
        if (temp.transaction.type==1) holder.binding.amountTv.setTextColorRes(R.color.green)
        else holder.binding.amountTv.setTextColorRes(R.color.red)
        if (temp.transaction.type==3){
            holder.binding.accountTv.text=temp.firstAccount.name+" -> "+temp.secondAccount!!.name
            holder.binding.amountTv.text = "0"
        }else{
            holder.binding.accountTv.text = temp.firstAccount.name
            holder.binding.amountTv.text = temp.transaction.amount.toString()
        }
        holder.binding.commentTv.text = temp.transaction.note
        holder.binding.dialogDateTv.text = temp.transaction.date
        if (temp.transaction.category_id==null){
            Glide.with(context).load(R.drawable.transfer).into(holder.binding.categoryImageView)
        }else Glide.with(context).load(temp.category!!.image_url).into(holder.binding.categoryImageView)

    }
}