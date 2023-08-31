package com.furkanbostan.moneymanagement.ui.calendarPage

import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.furkanbostan.moneymanagement.R
import com.furkanbostan.moneymanagement.database.Account
import com.furkanbostan.moneymanagement.database.Category
import com.furkanbostan.moneymanagement.database.Transactions
import com.furkanbostan.moneymanagement.database.service.ManagDataBase
import com.furkanbostan.moneymanagement.databinding.CalendarDayLayoutBinding
import com.furkanbostan.moneymanagement.databinding.CalendarHeaderBinding
import com.furkanbostan.moneymanagement.databinding.FragmentCalendarViewCalendarBinding
import com.furkanbostan.moneymanagement.ui.BaseFragment
import com.furkanbostan.moneymanagement.util.displayText
import com.furkanbostan.moneymanagement.util.setTextColorRes
import com.kizitonwose.calendar.core.*
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class CalendarViewCalendarFragment : BaseFragment(),CalendarDialog.OnDateSelectedListener {
    private lateinit var binding : FragmentCalendarViewCalendarBinding
    private val selectedDates = mutableSetOf<LocalDate>()
    private val today = LocalDate.now()
    private val transactions = MutableLiveData<ArrayList<Transactions>>()
    private var groupedTransactions = TreeMap<String, ArrayList<Transactions>>()
    private  var currentDate= MutableLiveData<LocalDate>()
    private val debounceHandler = Handler(Looper.getMainLooper())
    private val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Istanbul"))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentCalendarViewCalendarBinding.inflate(layoutInflater,container,false)

        //deleteAllTransaction()
        //insertAll()
        //insertAcc()
        //insertCateg()
        currentDate.value=LocalDate.now()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(150)
        val endMonth = currentMonth.plusMonths(150)
        val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY)
        setupCalendarHeader(daysOfWeek)
        setupMonthCalendar(startMonth, endMonth, currentMonth, daysOfWeek)

        transactions.observe(viewLifecycleOwner){ value->
            groupTransactionsByMonth()
            binding.calendarView.smoothScrollToMonth(currentDate.value!!.yearMonth)
            binding.calendarView.notifyMonthChanged(currentDate.value!!.yearMonth)
            calendar.set(Calendar.YEAR,currentDate.value!!.year)
            calendar.set(Calendar.MONTH,(currentDate.value!!.monthValue)-1)

        }
        currentDate.observe(viewLifecycleOwner){ value->
            filterTransactionsForMonthAndYear("%02d".format(value.monthValue),value.year.toString())
        }
        binding.calendarMonthTv.setOnClickListener {
            openCustomDialog()
        }
        binding.fabbAdd.setOnClickListener {
            findNavController().navigate(R.id.action_calendarFragment_to_addTransactionFragment)
        }
            
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
            val incomeTv= CalendarDayLayoutBinding.bind(view).incomeTv
            val expenseTv= CalendarDayLayoutBinding.bind(view).expenseTv
            val totalTv= CalendarDayLayoutBinding.bind(view).totalTv

            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        dateClicked(date = day.date)
                        calendar.set(Calendar.DAY_OF_MONTH,day.date.dayOfMonth)
                        ShowDayFragment(calendar).show(childFragmentManager,"dialog")
                    }
                }
            }
        }
        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                bindDate(data.date, container.dayTextView,container.incomeTv,
                        container.expenseTv,container.totalTv,container.dayLayout, data.position == DayPosition.MonthDate)
            }
        }

        binding.calendarView.setup(startMonth, endMonth, daysOfWeek.first())
        binding.calendarView.scrollToMonth(currentMonth)

        binding.calendarView.monthScrollListener = { month ->
            binding.calendarMonthTv.text= month.yearMonth.displayText()
        }

        binding.calendarNextMonth.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Butona dokunduğunda
                binding.calendarNextMonth.isEnabled = false // Butonu etkisizleştir
                binding.calendarView.findFirstVisibleMonth()?.let {
                    currentDate.postValue(currentDate.value?.plusMonths(1))
                    //binding.calendarView.smoothScrollToMonth(it.yearMonth.nextMonth)
                }
                debounceHandler.postDelayed({
                    binding.calendarNextMonth.isEnabled = true // Belirli bir süre sonra butonu tekrar etkinleştir
                }, 500) // 1000 ms = 1 saniye

                true
            } else {
                false
            }
        }



        binding.calendarPreviousMonth.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Butona dokunduğunda
                binding.calendarPreviousMonth.isEnabled = false // Butonu etkisizleştir
                binding.calendarView.findFirstVisibleMonth()?.let {
                    currentDate.postValue(currentDate.value?.minusMonths(1))
                    //binding.calendarView.smoothScrollToMonth(it.yearMonth.previousMonth)

                }
                debounceHandler.postDelayed({
                    binding.calendarPreviousMonth.isEnabled = true // Belirli bir süre sonra butonu tekrar etkinleştir
                }, 500) // 1000 ms = 1 saniye

                true
            } else {
                false
            }
        }

    }



    private fun bindDate(date: LocalDate, dayTextView: TextView,incomeTv:TextView, expenseTv:TextView,
                         totalTv:TextView, layout: ConstraintLayout, isSelectable: Boolean) {
        var incomeCount= 0f
        var expenseCount= 0f
        var totalCount = 0f
        incomeTv.text = ""
        expenseTv.text = ""
        totalTv.text = ""
        if(isSelectable) {
            if (!groupedTransactions.isEmpty()) {
                val formatDate= "%02d".format(date.dayOfMonth)
                if (groupedTransactions.containsKey(formatDate)){
                     for (i in groupedTransactions.getValue(formatDate)){
                         if (i.type!==3){
                             if (i.type==1) incomeCount += i.amount
                             else expenseCount += i.amount
                         }
                     }
                    totalCount = incomeCount - expenseCount
                    incomeTv.text = incomeCount.toInt().toString()
                    expenseTv.text = expenseCount.toInt().toString()
                    totalTv.text = totalCount.toInt().toString()
                }
            }
        }

       // println(DateTimeFormatter.ofPattern("dd-MM-YYYY", Locale("tr")).format(date))
        dayTextView.text = date.dayOfMonth.toString()
        if (isSelectable) {
            when {
                selectedDates.contains(date) -> {
                    layout.setBackgroundResource(R.drawable.selected_day_bg)
                    dayTextView.setTextColorRes(R.color.acik_mor)
                }
                today == date -> {
                    //layout.setBackgroundResource(R.drawable.calendar_today_bg)
                    dayTextView.setTextColorRes(R.color.black)
                }
                else -> {
                    layout.setBackgroundResource(R.color.acik_mor)
                    dayTextView.setTextColorRes(R.color.beyaz)
                }
            }
        } else {
            dayTextView.setTextColorRes(R.color.soluk_beyaz)
            dayTextView.background = null
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


    private fun filterTransactionsForMonthAndYear(month: String, year: String) {
        launch {
            val dao = ManagDataBase(requireContext()).transactionsDao()
            transactions.value?.clear()
            transactions.postValue(dao.getTransofMonthAndYear(month,year.trim()) as ArrayList<Transactions>)
        }
       /* calendar.set(Calendar.MONTH,(month.trimStart('0').toInt())-1)
        calendar.set(Calendar.YEAR,today.year)*/
    }


    private fun groupTransactionsByMonth() {
        groupedTransactions.clear()
        for (transaction in transactions.value!!) {
            val dateDay = transaction.date_day
            if (groupedTransactions.containsKey(dateDay)) {
                groupedTransactions[dateDay]?.add(transaction)
            } else {
                val newList = java.util.ArrayList<Transactions>()
                newList.add(transaction)
                groupedTransactions[dateDay] = newList
            }
        }

    }



    private fun insertAll(){
        val arraylist:ArrayList<Transactions>
        arraylist= ArrayList()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val dateSplit= today.plusDays(10).format(formatter).toString().split("-")
        val dateDay= dateSplit.get(0)
        val dateMonth= dateSplit.get(1)
        val dateYear= dateSplit.get(2)
        arraylist.add(Transactions(0,2,2,1,200f,today.plusDays(28).format(formatter).toString(),
            "kendi hedefim",dateDay,dateMonth,dateYear,0))
        arraylist.add(Transactions(0,1,2,1,200f,today.plusDays(7).format(formatter).toString(),
            "kendi hedefim",dateDay,dateMonth,dateYear,1))

        arraylist.add(Transactions(0,2,1,1,200f,today.minusDays(11).format(formatter).toString(),
            "kendi hedefim",dateDay,dateMonth,dateYear,0))
        arraylist.add(Transactions(0,2,2,1,200f,today.minusDays(28).format(formatter).toString(),
            "kendi hedefim",dateDay,dateMonth,dateYear,1))
        arraylist.add(Transactions(0,1,2,1,200f,today.minusDays(7).format(formatter).toString(),
            "kendi hedefim",dateDay,dateMonth,dateYear,0))

        arraylist.add(Transactions(0,2,1,1,200f,today.minusDays(11).format(formatter).toString(),
            "kendi hedefim",dateDay,dateMonth,dateYear,1))

        launch {
            val dao= ManagDataBase(requireContext()).transactionsDao()
            dao.insertAll(*arraylist.toTypedArray())
        }
    }


    private fun insertAcc(){
        val acc= Account(0,"Kredi Kartı",500f,100f,400f)
        launch {
            val dao= ManagDataBase(requireContext()).accountDao()
            dao.insert(acc)
        }
    }

    private fun insertCateg(){
        val cat= Category(0,"Araba",0f,0f,0f,R.drawable.car,0)
        launch {
            val dao=ManagDataBase(requireContext()).categoryDao()
            dao.insert(cat)
        }
    }
    private fun deleteAllTransaction(){
        launch {
            val dao=ManagDataBase(requireContext()).transactionsDao()
            dao.deleteAll()
        }
    }

    override fun onDateSelected(year: String, monthIndex: Int) {
        val localDate = LocalDate.of(
            (year.trim() .toInt()),
            monthIndex + 1,
            1)
        currentDate.postValue(localDate)
    }
    private fun openCustomDialog() {
        val dialog = CalendarDialog(requireContext(),calendar)
        dialog.onDateSelectedListener = this
        //dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT)
        dialog.window?.setGravity(Gravity.TOP)
        dialog.show()
    }

}