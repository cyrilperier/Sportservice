package com.uqac.pmm


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_activity_documentation.*
import kotlin.collections.HashMap


class FragmentDocumentationActivity : Fragment() {
    private var pompes: Button? = null
    private var button_entrainement : Button? = null
    private var addtraining: Button? = null
    val db = Firebase.firestore
    val map = linkedMapOf<String, String>()
    val array = mutableListOf<String>()
    val user = Firebase.auth.currentUser
    val uid = user?.uid
    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val doppelgangerFragment = FragmentDocumentationActivity()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            doppelgangerFragment.arguments = bundle
            return doppelgangerFragment
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_activity_documentation, container, false)


        addtraining = v.findViewById(R.id.button_add_training)
        addtraining?.setOnClickListener {
            confirmFireMissiles()
            Log.d("TAG","addtraining lancé")

        }
        return v
    }

    private fun openDialog(v: View?, map: LinkedHashMap<String, String>) {
        val array2 = mutableListOf<String>()
        for (v in map){
            array2.add(v.value)
        }
        val array = array2.toTypedArray()
        val newFragment = FireMissilesDialogFragment(array,map)
        fragmentManager?.let { newFragment.show(it, "missiles") }
    }
    fun confirmFireMissiles() {
        val newFragment = DialogFragmentAddTraining()
        fragmentManager?.let { newFragment.show(it, "missiles") }
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