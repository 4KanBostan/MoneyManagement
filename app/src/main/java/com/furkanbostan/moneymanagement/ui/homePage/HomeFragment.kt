package com.furkanbostan.moneymanagement.ui.homePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.database.Account
import com.furkanbostan.moneymanagement.database.GoalAndCategory
import com.furkanbostan.moneymanagement.database.Transactions
import com.furkanbostan.moneymanagement.database.TransactionsWithCategoryAndAccount
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.FragmentHomeBinding
import com.furkanbostan.moneymanagement.ui.BaseFragment
import com.furkanbostan.moneymanagement.ui.homePage.adapter.CustomRecyclerAdapter
import com.furkanbostan.moneymanagement.ui.homePage.adapter.HomeGoalsAdapter
import com.furkanbostan.moneymanagement.ui.homePage.adapter.HomeTransAdapter
import com.omega_r.libs.omegarecyclerview.viewpager.default_transformers.ScaleTransformer
import kotlinx.coroutines.launch
import java.time.LocalDate


class HomeFragment : BaseFragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var cardArray: ArrayList<Account>
    private lateinit var transactionList:ArrayList<TransactionsWithCategoryAndAccount>
    private lateinit var goalsList:ArrayList<GoalAndCategory>
    private lateinit var localDate: LocalDate
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        getAllAccount()
        getLastGoals()
        getLastTransaction()

        return binding.root
    }



    fun setAccountRcv(list:List<Account>){
        val liste= list as ArrayList<Account>
        binding.accountRecyc.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1f)
                .setMinScale(0.8f)
                .build()
        )
        binding.accountRecyc.adapter= CustomRecyclerAdapter(liste)

       /* binding.homeTransRecyc.layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        binding.homeTransRecyc.adapter= HomeTransAdapter(context,ornekTrans())
*/




    }

    private fun goalSetRcv(arrayList: ArrayList<GoalAndCategory>){
        binding.homeGoalsRecy.apply {
            layoutManager=LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            adapter= HomeGoalsAdapter(context,arrayList)
        }
    }


    fun getAllAccount():ArrayList<Account>{
        cardArray= ArrayList()
        launch {
            val dao= ManagDataBase(requireContext()).accountDao()
            cardArray=dao.getAllAccount() as ArrayList<Account>
            setAccountRcv(cardArray)
        }

        return cardArray
    }

    private fun getLastGoals(){
        var list= listOf<GoalAndCategory>()
        goalsList= ArrayList()
        launch {
            val dao = ManagDataBase(requireContext()).goalDao()
            list = dao.getGoalAndCategory()
           // goalsList= list as ArrayList<GoalAndCategory>
            if (list.isEmpty()){

            }
            else if (list.size<6){
                goalSetRcv(goalsList)
            }else{
                goalsList= ArrayList(list.subList(list.size-5,list.size-1))
                goalSetRcv(goalsList)
            }

        }

    }

    private fun getLastTransaction(){
        transactionList= ArrayList()
        launch {
            val dao=ManagDataBase(requireContext()).transactionsDao()
            transactionList=dao.getLastTransactionsWithCategoryAndAccount() as ArrayList<TransactionsWithCategoryAndAccount>
            setTransactionRcv(transactionList)
        }
    }

    private fun setTransactionRcv(list:List<TransactionsWithCategoryAndAccount>) {
        val liste = list as ArrayList<TransactionsWithCategoryAndAccount>

        binding.homeTransRecyc.apply {
            layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter = HomeTransAdapter(context,liste)
        }

    }
}