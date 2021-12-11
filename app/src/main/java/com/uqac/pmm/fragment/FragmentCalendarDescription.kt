package com.uqac.pmm.fragment

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.setFragmentResultListener
import com.uqac.pmm.R
import kotlinx.android.synthetic.main.fragment2_child.*
import kotlinx.android.synthetic.main.fragment_calendar_description.*

import com.github.sundeepk.compactcalendarview.domain.Event






class FragmentCalendarDescription : androidx.fragment.app.Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(com.uqac.pmm.R.layout.fragment_calendar_description, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //listen result from the ParentFragment through FragmentManager (ParentFragmentManager)
        //Once result receive show the Toast
        setFragmentResultListener("key_child2") {key, result->
            // get the result from bundle
            val events = result.get("child2") as ArrayList<Event>
            for (e in events){
                textView_description.text = e.data.toString()
            }
            //textView_description.text = result.get("child2").toString()
        }
    }
}