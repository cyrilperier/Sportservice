package com.uqac.pmm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_activity_profile.*


class FragmentProfileActivity : Fragment() {
    private var commencer_entrainement_button: Button? = null

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val doppelgangerFragment = FragmentProfileActivity()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            doppelgangerFragment.arguments = bundle
            return doppelgangerFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v =inflater.inflate(R.layout.fragment_activity_profile, container, false)
        openEntrainement(v)
        return v
    }

    fun openEntrainement(v: View?){
        commencer_entrainement_button = v?.findViewById(R.id.commencer_entrainement_button)
        commencer_entrainement_button?.setOnClickListener {

            with(it.context) {
                val intent = Intent(this, com.uqac.pmm.ListEntrainementActivity::class.java)
                intent.putExtra("commencer",true)
                startActivity(intent)

            }
        }

    }

}