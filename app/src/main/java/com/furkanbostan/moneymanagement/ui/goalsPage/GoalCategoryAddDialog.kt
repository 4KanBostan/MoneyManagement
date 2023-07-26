package com.furkanbostan.moneymanagement.ui.goalsPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.database.GoalCategory
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.GoalCategoryAddDialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class GoalCategoryAddDialog : DialogFragment(), CoroutineScope {
    private lateinit var binding:GoalCategoryAddDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding= GoalCategoryAddDialogBinding.inflate(layoutInflater,container,false)

        binding.goalCategorySaveButton.setOnClickListener {
            val name= binding.categoryNameEdittext.text.toString()
            val img_url= R.drawable.target
            val temp = GoalCategory(0, name, img_url)
            saveCategory(temp)
        }
        return binding.root
    }

    private fun saveCategory(category:GoalCategory) {
        launch {
            val dao = ManagDataBase(requireContext()).goalCategoryDao()
            dao.insert(category)
        }
    }


    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


}