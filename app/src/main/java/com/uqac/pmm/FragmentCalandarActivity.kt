package com.uqac.pmm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FragmentCalandarActivity : Fragment() {
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

        val v = inflater.inflate(R.layout.fragment_activity_calendar, container, false)
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val db = Firebase.firestore
        val map = linkedMapOf<String, String>()
        val array = mutableListOf<String>()
        val docRef = db.collection("users").document("$uid")
            .collection("trainings")
        docRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    map[document.id] = document.getString("title").toString()
                    array.add(document.getString("title").toString())
                    Log.d("TAG", "${document.id} => ${document.data}")
                    //Log.d("this is my array", "arr: " + Arrays.toString(array))
                    Log.d("this is my map", map.toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }


        return v
    }



}