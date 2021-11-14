package com.uqac.pmm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detail_entrainement.*

class FragmentDetailEntrainementActivity  : Fragment() {

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val detailEntrainementFragment = FragmentDetailEntrainementActivity()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            detailEntrainementFragment.arguments = bundle
            return detailEntrainementFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_entrainement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val position = requireArguments().getInt(ARG_POSITION)

        val EntrainementNamesArray = requireContext().resources.getStringArray(R.array.entrainement_names)


        exerciceDetail.text = EntrainementNamesArray[position]
    }


    }
