package com.furkanbostan.moneymanagement.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.databinding.FragmentReportBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


class ReportFragment : Fragment() {
    private lateinit var binding:FragmentReportBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentReportBinding.inflate(layoutInflater,container,false)

        pieChart()
        binding.popupButton.setOnClickListener{
            showPopUp()
        }

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

        val pieData=PieData(pieDataSet)

        binding.pieChartIncome.apply {
            data=pieData
            description=null
            centerText="Gelirler"
        }

        binding.pieChartExpense.apply {
            data=pieData
            description=null
            centerText="Giderler"
        }

    }


}