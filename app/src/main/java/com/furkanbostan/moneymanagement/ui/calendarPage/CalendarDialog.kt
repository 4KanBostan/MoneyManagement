package com.furkanbostan.moneymanagement.ui.calendarPage

import android.app.Dialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.util.setTextColorRes
import java.util.*

class CalendarDialog(context: Context, calendar: Calendar):Dialog(context), View.OnClickListener {
    private lateinit var monthTextViews: Array<TextView>
    private lateinit var calYearTextView: TextView
    private lateinit var nextYearImg: ImageView
    private lateinit var prevYearImg: ImageView
    private lateinit var canceldialog:ImageView
    private val calendar = calendar
    var onDateSelectedListener: OnDateSelectedListener? = null
    private val monthIndex = calendar.get(Calendar.MONTH)
    private var selectedMonthIndex = -1
    private var selectedYear = ""


    interface OnDateSelectedListener {
        fun onDateSelected(year: String, monthIndex: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_calendar)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window?.attributes)
        layoutParams.width = LayoutParams.MATCH_PARENT
        layoutParams.height = LayoutParams.WRAP_CONTENT
        window?.attributes = layoutParams

        canceldialog = findViewById(R.id.cancel_dialog_calendar)
        nextYearImg = findViewById(R.id.next_year)
        prevYearImg = findViewById(R.id.previous_year)
        calYearTextView = findViewById(R.id.cal_year_tw)
        val year = SimpleDateFormat("YYYY ", Locale("tr")).format(calendar.time)
        calYearTextView.text= year

        monthTextViews = arrayOf(
            findViewById(R.id.ocak),
            findViewById(R.id.subat),
            findViewById(R.id.mart),
            findViewById(R.id.nisan),
            findViewById(R.id.mayis),
            findViewById(R.id.haziran),
            findViewById(R.id.temmuz),
            findViewById(R.id.agustos),
            findViewById(R.id.eylul),
            findViewById(R.id.ekim),
            findViewById(R.id.kasim),
            findViewById(R.id.aralik),
        )
       monthTextViews[monthIndex].setTextColorRes(R.color.acik_mor)
        for (monthTextView in monthTextViews) {
            monthTextView.setOnClickListener(this)
        }
        calYearTextView.setOnClickListener(this)
        nextYearImg.setOnClickListener(this)
        prevYearImg.setOnClickListener(this)
        canceldialog.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.next_year -> {
                calYearTextView.text= (calYearTextView.text.toString().trim().toInt()+1).toString()
            }
            R.id.previous_year -> {
                calYearTextView.text= (calYearTextView.text.trim().toString().toInt()-1).toString()
            }
            R.id.cancel_dialog_calendar->{
                dismiss()
            }
            else -> {
                for (i in monthTextViews.indices) {
                    if (v == monthTextViews[i]) {
                        selectedMonthIndex = i
                        monthTextViews[monthIndex].setTextColorRes(R.color.laci)
                        monthTextViews[i].setTextColorRes(R.color.mor)
                        break
                    }
                }
                selectedYear= calYearTextView.text.toString()
                onDateSelectedListener?.onDateSelected(selectedYear, selectedMonthIndex)
                dismiss()
            }
        }


    }
}