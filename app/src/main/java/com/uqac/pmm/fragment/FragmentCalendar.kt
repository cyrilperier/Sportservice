package com.uqac.pmm.fragment
import android.R
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
//import android.widget.Toolbar
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.github.sundeepk.compactcalendarview.domain.Event
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.FragmentCalandarActivity
import kotlinx.android.synthetic.main.fragment_profil_information.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_calendar.view.*


class FragmentCalendar : Fragment() {
    private lateinit var toolbar: Toolbar
    private val dateFormatForMonth = SimpleDateFormat("MMMM- yyyy", Locale.getDefault())

    lateinit var inflater : LayoutInflater
    lateinit var v : View
    lateinit var calendarView : CalendarView
    val map = linkedMapOf<String, ArrayList<String>>()
    var compactCalendar: CompactCalendarView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        v = inflater.inflate(com.uqac.pmm.R.layout.fragment_calendar,null)
        readTrainings()

        return v
    }

    fun gotoToday() {

        // Set any date to navigate to particular date
        compactCalendar?.setCurrentDate(Calendar.getInstance(Locale.getDefault()).time)

    }
    fun readTrainings() {

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user?.uid


        db.collection("users").document("$uid").collection("trainings")
            .get()
            .addOnSuccessListener { documents ->
                documents.map {
                    if (it.get("date") != null)
                    {
                        val timestamp = it.get("date") as com.google.firebase.Timestamp
                        val name=it.get("title")
                        val exerciseField = ArrayList<String>()
                        exerciseField.add(0, timestamp.seconds.toString())
                        exerciseField.add(1, name.toString())
                        map[it.id] = exerciseField
                    }
                }
                compactCalendar = v.findViewById(com.uqac.pmm.R.id.compactcalendar_view) as CompactCalendarView
                compactCalendar?.setUseThreeLetterAbbreviation(true)
                toolbar = v.toolbar as Toolbar
                toolbar.title = dateFormatForMonth.format(compactCalendar?.getFirstDayOfCurrentMonth())


                for (v in map) {
                    val time = v.value[0] + "000"
                    Log.d("TIME", time)
                    val data = ArrayList<String>()
                    data.add(0, v.key)
                    data.add(0, v.value[1])
                    val ev1 = Event(Color.argb(255, 255, 152, 0), time.toLong(), data)
                    compactCalendar?.addEvent(ev1)
                }
                compactCalendar?.setListener(object : CompactCalendarViewListener {
                    override fun onDayClick(dateClicked: Date) {
                        //val context: Context = getActivity().getApplicationContext()
                        Log.d("TAG DATE",dateClicked.toString())
                        if (dateClicked.toString().compareTo("Mon Dec 13 00:00:00 GMT 2021") == 0) {
                            Toast.makeText(context, "Teacher's Professional Day", Toast.LENGTH_SHORT).show()
                        }
                        val events = compactCalendar?.getEvents(dateClicked)
                        Log.d("TAG", "Day was clicked: $dateClicked with events $events")
                        setFragmentResult("key_parent", bundleOf("parent" to events))
                    }

                    override fun onMonthScroll(firstDayOfNewMonth: Date) {
                        // Changes toolbar title on monthChange
                        toolbar.title = dateFormatForMonth.format(firstDayOfNewMonth);
                    }
                })
                gotoToday()

            }

    }
}