package com.uqac.pmm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentDetailEntrainementActivity  : Fragment() {

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val DetailEntrainementFragment = FragmentDetailEntrainementActivity()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            DetailEntrainementFragment.arguments = bundle
            return DetailEntrainementFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_entrainement, container, false)
    }


    }
