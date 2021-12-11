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
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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

import kotlinx.android.synthetic.main.fragment_activity_calendar.*
import java.text.SimpleDateFormat
import com.github.sundeepk.compactcalendarview.domain.Event
import com.uqac.pmm.fragment.FragmentCalendar
import com.uqac.pmm.fragment.FragmentCalendarDescription


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
        val v = inflater.inflate(R.layout.fragment_first, container, false)
        return v
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //add child fragment
        childFragmentManager.beginTransaction().replace(R.id.fragmentCalendar,FragmentCalendar()).commit()
        childFragmentManager.beginTransaction().add(R.id.fragmentCalendarDescription,FragmentCalendarDescription()).commit()

        //listen result from the ChildFragment through childFragmentManager
        childFragmentManager.setFragmentResultListener("key_parent", this) {key, result->
            // get the result from bundle
            val result = result.get("parent")
            Log.d("RESULTAT", result.toString())
            //Toast.makeText(requireContext(), "$key: $stringResult", Toast.LENGTH_SHORT).show()
            childFragmentManager.setFragmentResult("key_child2", bundleOf("child2" to result))
        }

    }

}