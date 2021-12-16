package com.uqac.pmm

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.model.Serie
import kotlinx.android.synthetic.main.list_entrainement_view.view.entrainement_name_textview
import kotlinx.android.synthetic.main.list_serie_view.view.*

import com.github.sundeepk.compactcalendarview.domain.Event
import kotlinx.android.synthetic.main.list_calendar_view.view.*
import java.security.Timestamp
import java.util.*
import kotlin.collections.ArrayList

class ListCalendarAdapter(val events : ArrayList<Event>, val context : Context) : RecyclerView.Adapter<ListCalendarAdapter.CalendarViewHolder>() {


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class CalendarViewHolder(val listCalendarView: View) : RecyclerView.ViewHolder(listCalendarView)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.list_calendar_view, parent, false)

        return CalendarViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val event: Event = events[position]
        var data = event.data as java.util.ArrayList<String>
        holder.listCalendarView.event_name_textview.text = "${data[0]}"
        holder.listCalendarView.trainChange.setOnClickListener {
            with(it.context){
                val intent = Intent(this, DetailEntrainementActivity::class.java)
                intent.putExtra("entrainement_name",data[0] )
                intent.putExtra("idFirebase",data[1])
                startActivity(intent)
            }

        }
        holder.listCalendarView.playTraining.setOnClickListener {
            with(it.context){
                val intent = Intent(this, ListEntrainementActivity::class.java)
                intent.putExtra("commencer",true)
                startActivity(intent)




            }
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = events.size
}