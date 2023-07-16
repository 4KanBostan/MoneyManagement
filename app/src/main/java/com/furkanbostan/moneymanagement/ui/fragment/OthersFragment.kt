package com.furkanbostan.moneymanagement.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.databinding.FragmentOthersBinding


class OthersFragment : Fragment() {
    private lateinit var binding:FragmentOthersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentOthersBinding.inflate(layoutInflater,container,false)


        return binding.root
    }


}