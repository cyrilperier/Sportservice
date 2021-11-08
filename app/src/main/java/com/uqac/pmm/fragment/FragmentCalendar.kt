package com.uqac.pmm.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uqac.pmm.R


class FragmentCalendar : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        vg: ViewGroup?,
        savedInstanceState: Bundle
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, vg, false)
    }
}
