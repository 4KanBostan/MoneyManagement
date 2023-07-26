package com.furkanbostan.moneymanagement.ui.goalsPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.furkanbostan.moneymanagement.database.GoalCategory
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.FragmentGoalCategoriesBinding
import com.furkanbostan.moneymanagement.ui.BaseFragment
import com.furkanbostan.moneymanagement.ui.goalsPage.adapter.GoalCategoriesAdapter
import kotlinx.coroutines.launch

class GoalCategoriesFragment : BaseFragment() {
    private lateinit var binding:FragmentGoalCategoriesBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentGoalCategoriesBinding.inflate(layoutInflater,container,false)

        getAllCategory()


        binding.addButton.setOnClickListener {
            GoalCategoryAddDialog().show(childFragmentManager,"addGoalCategoryDialog")
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun getAllCategory(){
        launch {
            val dao= ManagDataBase(requireContext()).goalCategoryDao()
            val list:List<GoalCategory> =dao.getAllGoals()
            setrcv(list)
        }
    }

    private fun setrcv(list: List<GoalCategory>) {
        val liste= list as ArrayList<GoalCategory>
        binding.goalCategoriesRcv.apply {
            layoutManager = GridLayoutManager(context,3)
            adapter= GoalCategoriesAdapter(requireContext(),liste)
        }
    }


}