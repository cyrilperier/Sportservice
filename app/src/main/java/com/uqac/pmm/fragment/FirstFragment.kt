package com.uqac.pmm.fragment


import android.os.Bundle

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment


class FirstFragment : Fragment() {
    // Store instance variables
    private var title: String? = null
    private var page = 0

    // Store instance variables based on arguments passed
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments?.getInt("someInt", 0)!!
        title = arguments?.getString("someTitle")!!
    }

    // Inflate the view for the fragment based on layout XML
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(com.uqac.pmm.R.layout.fragment_calendar_description, container, false)

        return view
    }

    companion object {
        // newInstance constructor for creating fragment with arguments
        fun newInstance(page: Int, title: String?): FirstFragment {
            val fragmentFirst = FirstFragment()
            val args = Bundle()
            args.putInt("someInt", page)
            args.putString("someTitle", title)
            fragmentFirst.setArguments(args)
            return fragmentFirst
        }
    }
}