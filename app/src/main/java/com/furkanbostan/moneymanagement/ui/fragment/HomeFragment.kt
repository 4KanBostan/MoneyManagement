package com.furkanbostan.moneymanagement.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkanbostan.moneymanagement.databinding.FragmentHomeBinding
import com.furkanbostan.moneymanagement.ui.adapter.CustomRecyclerAdapter
import com.furkanbostan.moneymanagement.ui.fragment.model.HomeAccount
import com.omega_r.libs.omegarecyclerview.viewpager.ViewPagerLayoutManager
import com.omega_r.libs.omegarecyclerview.viewpager.default_transformers.ScaleTransformer


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var cardArray: ArrayList<HomeAccount>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        setrcv()
        return binding.root

    }
    fun setrcv(){
        binding.accountRecyc.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1f)
                .setMinScale(0.8f)
                .build()
        )
        binding.accountRecyc.adapter=CustomRecyclerAdapter(addItem())
    }

    fun addItem():ArrayList<HomeAccount>{
        cardArray= ArrayList()

        cardArray.add(HomeAccount("Toplam Bakiye",2500,10000,7500))
        cardArray.add(HomeAccount("Kredi Kartı",2500,10000,7500))
        cardArray.add(HomeAccount("Banka Hesabı",2500,10000,7500))
        cardArray.add(HomeAccount("Yatırım Hesabım",2500,10000,7500))

        return cardArray
    }

}