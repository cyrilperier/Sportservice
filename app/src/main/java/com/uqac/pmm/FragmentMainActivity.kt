package com.uqac.pmm


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FragmentMainActivity : Fragment() {
    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val doppelgangerFragment = FragmentMainActivity()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            doppelgangerFragment.arguments = bundle
            return doppelgangerFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_activity_main, container, false)
    }





    fun add_exercice(){
        Log.d("TAG","add exercice")
        val db = Firebase.firestore

        val exercice = hashMapOf(
            "Nom" to "burpees",
            "Type" to "sans machine",
            "Zone" to "corps"
        )

// Add a new document with a generated ID
        db.collection("Exercice")
            .add(exercice)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d("TAG", "Error adding document", e)
            }

    }
    fun read_exercice() {
        Log.d("TAG","read exercice")
        val db = Firebase.firestore
        db.collection("Exercice")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents.", exception)
            }
    }
}