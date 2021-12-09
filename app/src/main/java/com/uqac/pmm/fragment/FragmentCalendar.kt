package com.uqac.pmm.fragment

import android.app.DatePickerDialog
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import com.uqac.pmm.FragmentDetailEntrainementActivity
import com.uqac.pmm.R
import kotlinx.android.synthetic.main.activity_list_serie.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.util.*
import kotlin.properties.Delegates
import android.content.Intent
import android.widget.CalendarView.OnDateChangeListener


class FragmentCalendar : Fragment() {
  var selectedDate by Delegates.notNull<Long>()
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    var selectedDay : Int = day
    var selectedMonth : Int = month
    var selectedYear : Int = year
    lateinit var inflater : LayoutInflater
    lateinit var v : View
    lateinit var calendarView : CalendarView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        this.inflater = requireActivity().layoutInflater
        v = inflater.inflate(R.layout.fragment_calendar,null)
        calendarView = v.findViewById(R.id.calendarView) as CalendarView

        Log.d("TEST",calendarView.date.toString())
            return inflater.inflate(R.layout.fragment_calendar, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        Log.d("TEST",calendarView.date.toString())

        calendarView.setOnDateChangeListener( CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->

            onSelectedDayChange(view, year, month,
                dayOfMonth) {

                selectedYear = year;
                selectedMonth = month;
                selectedDay = dayOfMonth;
                Log.d("TEST", year.toString())

            }
        })




    }

    private fun onSelectedDayChange(
        calendarView: CalendarView,
        year: Int,
        month: Int,
        day: Int,
        function: () -> Unit
    ) {
        Log.d("TEST", year.toString())

    }


}