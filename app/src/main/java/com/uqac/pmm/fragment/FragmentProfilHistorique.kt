package com.uqac.pmm.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.sundeepk.compactcalendarview.domain.Event
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.ListCalendarAdapter
import com.uqac.pmm.ListEntrainementAdapter
import com.uqac.pmm.ListSerieAdapter
import com.uqac.pmm.R
import com.uqac.pmm.model.Entrainement
import kotlinx.android.synthetic.main.activity_list_entrainement.*
import kotlinx.android.synthetic.main.activity_list_serie.*
import kotlinx.android.synthetic.main.fragment_calendar_description.*
import kotlinx.android.synthetic.main.list_entrainement_view.view.*
import kotlin.properties.Delegates

class FragmentProfilHistorique : Fragment() {

   val entrainements: MutableList<Entrainement> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_list_entrainement, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTraining.setVisibility(View.INVISIBLE)
        read_entrainement()
        //listen result from the ParentFragment through FragmentManager (ParentFragmentManager)
        //Once result receive show the Toast

    }

    fun read_entrainement() {

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user?.uid



        db.collection("users").document("$uid")
            .collection("trainings")
            .get()
            .addOnSuccessListener { results ->
                db.collection("users").document("$uid")
                    .collection("history")
                    .document("4jCppJ7gHeqSbDg69kA7")
                    .collection("trainings")
                    .get()
                    .addOnSuccessListener { listentrainements->

                        var listentrainement= listentrainements.map{
                            it.id
                        } as MutableList<String>
                        Log.d("TEST", listentrainement.toString())

                        results.map {
                            for (id in listentrainement)
                                if (id == it.id) {
                                    entrainements.add(Entrainement(null, it.id, it.get("title").toString(),
                                        it.get("dates") as ArrayList<Timestamp>?
                                    ))
                                }
                        }
                        list_entrainements_recyclerview.apply {
                            // set a LinearLayoutManager to handle Android
                            // RecyclerView behavior
                            layoutManager = LinearLayoutManager(activity)
                            // set the custom adapter to the RecyclerView
                            adapter = context?.let { ListEntrainementAdapter(entrainements, it,false,true) }
                        }


                    }
            }


    }
}