package com.furkanbostan.moneymanagement.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.databinding.FragmentHomeBinding
import com.furkanbostan.moneymanagement.ui.adapter.CustomRecyclerAdapter
import com.furkanbostan.moneymanagement.ui.adapter.HomeGoalsAdapter
import com.furkanbostan.moneymanagement.ui.adapter.HomeTransAdapter
import com.furkanbostan.moneymanagement.model.HomeAccount
import com.furkanbostan.moneymanagement.model.Goals
import com.furkanbostan.moneymanagement.model.Transaction
import com.omega_r.libs.omegarecyclerview.viewpager.default_transformers.ScaleTransformer
import java.time.LocalDate


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var cardArray: ArrayList<HomeAccount>
    private lateinit var transactionList:ArrayList<Transaction>
    private lateinit var goalsList:ArrayList<Goals>
    private lateinit var localDate: LocalDate
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        setrcv(requireContext())
        return binding.root
    }



    fun setrcv(context:Context){
        binding.accountRecyc.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1f)
                .setMinScale(0.8f)
                .build()
        )
        binding.accountRecyc.adapter=CustomRecyclerAdapter(addItem())

        binding.homeTransRecyc.layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        binding.homeTransRecyc.adapter=HomeTransAdapter(context,ornekTrans())


        binding.homeGoalsRecy.apply {
            layoutManager=LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            adapter=HomeGoalsAdapter(context,addGoals())
        }


    }


    fun addItem():ArrayList<HomeAccount>{
        cardArray= ArrayList()

        cardArray.add(HomeAccount("Toplam Bakiye",2500,10000,7500))
        cardArray.add(HomeAccount("Kredi Kartı",2500,10000,7500))
        cardArray.add(HomeAccount("Banka Hesabı",2500,10000,7500))
        cardArray.add(HomeAccount("Yatırım Hesabım",2500,10000,7500))

        return cardArray
    }
    fun ornekTrans():ArrayList<Transaction>{
        val t = Transaction("Eğlence","Banka Kartı",3500f,"22/3/2023","Oyun makinesi", R.drawable.cutlery)
        val t1 = Transaction("Eğlence","Banka Kartı",3500f,"22/3/2023","Oyun makinesi", R.drawable.cutlery)
        val t2 = Transaction("Eğlence","Banka Kartı",3500f,"22/3/2023","Oyun makinesi", R.drawable.cutlery)
        transactionList=ArrayList()
        transactionList.add(t)
        transactionList.add(t1)
        transactionList.add(t2)

        return transactionList
    }

    fun addGoals():ArrayList<Goals>{
        localDate= LocalDate.now()
        goalsList= ArrayList()
        goalsList.add(Goals("Araba",50,2500,4000,R.drawable.car,localDate))
        goalsList.add(Goals("Araba",50,2500,4000,R.drawable.car,localDate))
        goalsList.add(Goals("Araba",50,2500,4000,R.drawable.car,localDate))
        goalsList.add(Goals("Araba",50,2500,4000,R.drawable.car,localDate))

        return goalsList
    }
}