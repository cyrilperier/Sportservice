package com.uqac.pmm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class FragmentProfileActivity : Fragment() {
    private var title: String? = null
    private var page = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments?.getInt("someInt", 0)!!
        title = arguments?.getString("someTitle")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_activity_profile, container, false)

        return view
    }
    companion object {
        fun newInstance(page: Int, title: String?): FragmentProfileActivity {
            val fragmentProfile = FragmentProfileActivity()
            val args = Bundle()
            args.putInt("someInt", page)
            args.putString("someTitle", title)
            fragmentProfile.setArguments(args)
            return fragmentProfile
        }
    }
}