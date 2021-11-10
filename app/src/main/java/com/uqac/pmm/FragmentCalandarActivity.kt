package com.uqac.pmm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.uqac.pmm.fragment.FragmentCalendar

class FragmentCalandarActivity : Fragment() {
    private var title: String? = null
    private var page = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments?.getInt("someInt", 0)!!
        title = arguments?.getString("someTitle")
    }

    /*
        override fun onCreateView(
            inflater: LayoutInflater,
            vg: ViewGroup?,
            savedInstanceState: Bundle
        ): View? {
            return inflater.inflate(R.layout.fragment_calendar, vg, false)
        }*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_activity_calendar, container, false)

        return view
    }

    companion object {
        fun newInstance(page: Int, title: String?): FragmentCalandarActivity {
            val fragmentCalandar = FragmentCalandarActivity()
            val args = Bundle()
            args.putInt("someInt", page)
            args.putString("someTitle", title)
            fragmentCalandar.setArguments(args)
            return fragmentCalandar
        }
    }
}