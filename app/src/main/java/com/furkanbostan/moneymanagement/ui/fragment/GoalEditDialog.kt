package com.furkanbostan.moneymanagement.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.database.Goal
import com.furkanbostan.moneymanagement.database.GoalCategory
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.FragmentGoalEditDialogBinding
import com.furkanbostan.moneymanagement.ui.BaseFragment
import com.furkanbostan.moneymanagement.ui.DataListener
import com.furkanbostan.moneymanagement.ui.adapter.GoalCardAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class GoalEditDialog : BottomSheetDialogFragment(),CoroutineScope{
    private lateinit var binding:FragmentGoalEditDialogBinding
    private lateinit var itemArray:ArrayList<GoalCategory>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentGoalEditDialogBinding.inflate(layoutInflater,container,false)
        getAllGoal()

        binding.goalCardExitImg.setOnClickListener {
            dialog?.dismiss()
        }
        binding.goalCardEditImg.setOnClickListener{
            findNavController().navigate(R.id.action_addGoalFragment_to_goalCategoriesFragment)
        }
        return binding.root
    }


    fun setRcv( list: List<GoalCategory>) {
        val liste: ArrayList<GoalCategory> = list as ArrayList<GoalCategory>
        binding.goalBottomCardRcv.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = GoalCardAdapter(context, list,object : OnItemClickListener{
                override fun onItemClick(name: GoalCategory) {
                    val mainFragment = parentFragment as? AddGoalFragment
                    mainFragment?.updateEditText(name)
                    dismiss()
                }
            })
        }
    }

        private fun getAllGoal() {
            var list = listOf<GoalCategory>()
            launch {
                val dao = ManagDataBase(requireContext()).goalCategoryDao()
                list = dao.getAllGoals()
                setRcv(list)
                itemArray = list as ArrayList<GoalCategory>
            }

        }


        private val job = Job()
        override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


        override fun onDestroy() {
            super.onDestroy()
            job.cancel()
        }

    interface OnItemClickListener {
        fun onItemClick(name: GoalCategory)
    }


}