package com.furkanbostan.moneymanagement.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.marginStart
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.databinding.CalendarDayLayoutBinding
import com.furkanbostan.moneymanagement.databinding.CalendarHeaderBinding
import com.furkanbostan.moneymanagement.databinding.FragmentCalendarViewCalendarBinding
import com.furkanbostan.moneymanagement.util.displayText
import com.furkanbostan.moneymanagement.util.setTextColorRes
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kizitonwose.calendar.core.*
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth


class CalendarViewCalendarFragment : Fragment() {
    private lateinit var binding : FragmentCalendarViewCalendarBinding
    private lateinit var dialog: BottomSheetDialog
    private val selectedDates = mutableSetOf<LocalDate>()
    private val today = LocalDate.now()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentCalendarViewCalendarBinding.inflate(layoutInflater,container,false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(10)
        val endMonth = currentMonth.plusMonths(10)
        val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY)
        setupCalendarHeader(daysOfWeek)
        setupMonthCalendar(startMonth, endMonth, currentMonth, daysOfWeek)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupMonthCalendar(
        startMonth: YearMonth,
        endMonth: YearMonth,
        currentMonth: YearMonth,
        daysOfWeek: List<DayOfWeek>,
    ) {
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            val dayTextView = CalendarDayLayoutBinding.bind(view).calendarDayTv
            val dayLayout = CalendarDayLayoutBinding.bind(view).calendarDayLayout
            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        dateClicked(date = day.date)
                        ShowDayFragment(day.date).show(childFragmentManager,"dialog")
                        //openBottomSheet()
                    }
                }
            }
        }
        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                bindDate(data.date, container.dayTextView,container.dayLayout, data.position == DayPosition.MonthDate)
            }
        }

        binding.calendarView.setup(startMonth, endMonth, daysOfWeek.first())
        binding.calendarView.scrollToMonth(currentMonth)

        binding.calendarView.monthScrollListener = { month ->
            binding.calendarMonthTv.text= month.yearMonth.displayText()
        }

        binding.calendarNextMonth.setOnClickListener{
            binding.calendarView.findFirstVisibleMonth()?.let {
                binding.calendarView.smoothScrollToMonth(it.yearMonth.nextMonth)
            }
        }
        binding.calendarPreviousMonth.setOnClickListener{
            binding.calendarView.findFirstVisibleMonth()?.let {
                binding.calendarView.smoothScrollToMonth(it.yearMonth.previousMonth)
            }
        }

    }



    private fun bindDate(date: LocalDate, textView: TextView, layout: ConstraintLayout, isSelectable: Boolean) {
        textView.text = date.dayOfMonth.toString()
        if (isSelectable) {
            when {
                selectedDates.contains(date) -> {
                    layout.setBackgroundResource(R.drawable.selected_day_bg)
                    textView.setTextColorRes(R.color.acik_mor)
                }
                today == date -> {
                    //layout.setBackgroundResource(R.drawable.calendar_today_bg)
                    textView.setTextColorRes(R.color.black)
                }
                else -> {
                    layout.setBackgroundResource(R.color.acik_mor)
                      textView.setTextColorRes(R.color.beyaz)
                }
            }
        } else {
            textView.setTextColorRes(R.color.soluk_beyaz)
            textView.background = null
        }
    }
    private fun dateClicked(date: LocalDate) {
        if (selectedDates.contains(date)) {
            selectedDates.remove(date)
        } else {
            if (!selectedDates.isEmpty()){
                val oldDate = selectedDates.first()
                binding.calendarView.notifyDateChanged(oldDate)
            }
            selectedDates.clear()
            selectedDates.add(date)
        }
        binding.calendarView.notifyDateChanged(date)
    }



    fun setupCalendarHeader(daysOfWeek: List<DayOfWeek>){
        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
        }

        val typeFace = Typeface.create("sans-serif-light", Typeface.NORMAL)
        binding.calendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = data.yearMonth
                    container.legendLayout.children.map { it as TextView }
                        .forEachIndexed { index, tv ->
                            tv.text = daysOfWeek[index].displayText(uppercase = true)
                            tv.setTextColorRes(R.color.beyaz)
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                            tv.typeface = typeFace
                        }
                }
            }
        }
    }

    fun openBottomSheet(){
        val dialogView = layoutInflater.inflate(R.layout.layout_bottom_sheet,null)
        dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        dialog.show()
    }

}