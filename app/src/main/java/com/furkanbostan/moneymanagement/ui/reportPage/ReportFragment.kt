package com.furkanbostan.moneymanagement.ui.reportPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.database.Expense
import com.furkanbostan.moneymanagement.database.Income
import com.furkanbostan.moneymanagement.database.IncomeWithCategoryAndAccount
import com.furkanbostan.moneymanagement.database.TransactionsWithCategoryAndAccount
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.FragmentReportBinding
import com.furkanbostan.moneymanagement.model.ReportChartCategory
import com.furkanbostan.moneymanagement.ui.BaseFragment
import com.furkanbostan.moneymanagement.ui.reportPage.adapter.PieChartAdapter
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.launch


class ReportFragment : BaseFragment() {
    private lateinit var binding:FragmentReportBinding
    private lateinit var itemArray:ArrayList<ReportChartCategory>
    private lateinit var itemIncomeArray:ArrayList<ReportChartCategory>
    private var incomeList=MutableLiveData<ArrayList<IncomeWithCategoryAndAccount>>()
    private var expenseList=MutableLiveData<ArrayList<Expense>>()
    private var groupedIncomeList= HashMap<String,ArrayList<IncomeWithCategoryAndAccount>>()
    private var groupedExpenseList= HashMap<String,ArrayList<Expense>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentReportBinding.inflate(layoutInflater,container,false)

        pieChartIncome()
        pieChartExpense()

        binding.popupButton.setOnClickListener{
            showPopUp()
        }
        setRcv()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        incomeList.observe(viewLifecycleOwner){ value->
            groupedIncomeByMonth()
        }
    }

    private fun groupedIncomeByMonth() {
        groupedIncomeList.values.clear()

        for (income in incomeList.value!!){
            val categoryName= income.category.name
            if (groupedIncomeList.containsKey(categoryName)){
                groupedIncomeList[categoryName]?.add(income)
            }else{
                val newList = ArrayList<IncomeWithCategoryAndAccount>()
                newList.add(income)
                groupedIncomeList[categoryName] = newList
            }
        }
        setIncomeRcv(groupedIncomeList)
    }

    private fun setIncomeRcv(groupedIncomeList:HashMap<String,ArrayList<IncomeWithCategoryAndAccount>>) {
        itemIncomeArray=ArrayList()
        for(item in groupedIncomeList){
            var temp = 0f
            for (i in item.value){
                temp += i.income.amount
            }
            var rate = totalIncomeAmount()
            rate= ((temp/rate)*100).toInt()
            itemIncomeArray.add(ReportChartCategory(rate,item.key,temp.toInt()))
        }
        binding.reportIncomeRcv.apply {
            layoutManager=LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            adapter = PieChartAdapter(itemIncomeArray)
        }
    }
    private fun totalIncomeAmount():Int{
        var temp = 0f
        for (i in incomeList.value!!){
            temp+= i.income.amount
        }
        return temp.toInt()
    }



    private fun pieChartIncome(){
        val pieEntry= arrayListOf<PieEntry>()



        pieEntry.add(PieEntry(30f,"Eğlence"))
        pieEntry.add(PieEntry(20f,"Yemek"))
        pieEntry.add(PieEntry(15f,"Kitap"))
        pieEntry.add(PieEntry(35f,"Yakıt"))

        val pieDataSet=PieDataSet(pieEntry,"Giderler")
        pieDataSet.valueTextSize=15f
        pieDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        pieDataSet.xValuePosition=PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.yValuePosition=PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.valueTextColor=R.color.laci
        val pieData=PieData(pieDataSet)



        binding.pieChartIncome.apply {
            data=pieData
            description=null
            setEntryLabelColor(R.color.laci)
            centerText="Gelirler"
        }


    }

    private fun pieChartExpense(){
        val pieEntry= arrayListOf<PieEntry>()
        pieEntry.add(PieEntry(30f,"Eğlence"))
        pieEntry.add(PieEntry(20f,"Yemek"))
        pieEntry.add(PieEntry(15f,"Kitap"))
        pieEntry.add(PieEntry(35f,"Yakıt"))

        val pieDataSet=PieDataSet(pieEntry,"Giderler")
        pieDataSet.valueTextSize=15f
        pieDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        pieDataSet.xValuePosition=PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.yValuePosition=PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.valueTextColor=R.color.laci
        val pieData=PieData(pieDataSet)


        binding.pieChartExpense.apply {
            data=pieData
            description=null
            setEntryLabelColor(R.color.laci)
            centerText="Giderler"
        }

    }

    fun setRcv(){
        itemArray= ArrayList()

        itemArray.add(ReportChartCategory(25,"Eğlence",2500))
        itemArray.add(ReportChartCategory(25,"Yemek",2500))
        itemArray.add(ReportChartCategory(25,"Kitap",2500))
        itemArray.add(ReportChartCategory(25,"Yakıt",2500))

        binding.reportIncomeRcv.apply {
            layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter= PieChartAdapter(itemArray)
        }
        binding.reportExpenseRcv.apply {
            layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter= PieChartAdapter(itemArray)
        }

    }

    private fun getIncomeForMonth(month:String){
        launch {
            val dao= ManagDataBase(requireContext()).incomeDao()
            incomeList.value?.clear()
            incomeList.postValue(dao.getAllOfMonth(month) as ArrayList<IncomeWithCategoryAndAccount>)
        }
    }

    fun showPopUp(){
        val button:AppCompatButton=binding.popupButton
        val popupMenu = PopupMenu(context, button)

        // Inflating popup menu from popup_menu.xml file
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.month-> button.text=menuItem.title
                R.id.year-> button.text=menuItem.title
                R.id.period-> button.text=menuItem.title
            }
            true
        }
        // Showing the popup menu
        popupMenu.show()
    }
}