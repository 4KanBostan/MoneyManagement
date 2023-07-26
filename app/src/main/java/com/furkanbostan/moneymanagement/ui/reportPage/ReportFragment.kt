package com.furkanbostan.moneymanagement.ui.reportPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.databinding.FragmentReportBinding
import com.furkanbostan.moneymanagement.model.ReportChartCategory
import com.furkanbostan.moneymanagement.ui.reportPage.adapter.PieChartAdapter
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


class ReportFragment : Fragment() {
    private lateinit var binding:FragmentReportBinding
    private lateinit var itemArray:ArrayList<ReportChartCategory>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentReportBinding.inflate(layoutInflater,container,false)

        pieChart()
        binding.popupButton.setOnClickListener{
            showPopUp()
        }
        setRcv()

        return binding.root
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


    fun pieChart(){
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


}