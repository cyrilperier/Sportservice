package com.uqac.pmm.fragment

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import com.uqac.pmm.R
import kotlinx.android.synthetic.main.fragment_calendar_description.*






class FragmentCalendarDescription : androidx.fragment.app.Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {

            return inflater.inflate(com.uqac.pmm.R.layout.fragment_calendar_description, container, false)
        }
    }