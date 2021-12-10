package com.uqac.pmm

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.LinkedHashMap
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import java.text.SimpleDateFormat


class FragmentCalandarActivity : Fragment() {
    var compactCalendar: CompactCalendarView? = null
    private val dateFormatMonth: SimpleDateFormat = SimpleDateFormat("MMMM- yyyy", Locale.getDefault())
    private var pompes: Button? = null
    val db = Firebase.firestore
    val map = linkedMapOf<String, String>()
    val array = mutableListOf<String>()
    val user = Firebase.auth.currentUser
    val uid = user?.uid
    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val doppelgangerFragment = FragmentCalandarActivity()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            doppelgangerFragment.arguments = bundle
            return doppelgangerFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_activity_calendar, container, false)

        return v
    }

/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        class DayViewContainer(view: View) : ViewContainer(view) {
            val textView = view.findViewById<TextView>(R.id.calendarDayText)

            // With ViewBinding
            // val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
        }
        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textView.text = day.date.dayOfMonth.toString()
            }
        }
    }*/

}