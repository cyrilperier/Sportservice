package com.uqac.pmm


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.HashMap


class FragmentDocumentationActivity : Fragment() {
    private var pompes: Button? = null
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
        pompes = v.findViewById(R.id.button_pompes)
        pompes?.setOnClickListener {
            openDialog(v,map)
            Log.d("TAG","t'es bien ici")

        }
        return v
    }
/*
    private fun taskPopUp(v: View?) {
        //var dialog = customDialogFragment()
        val dialog = Dialog(requireActivity())
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.fragment_custom_dialog)
        val fragmentManager = (activity as FragmentActivity).supportFragmentManager
        val ll = dialog.findViewById<View>(R.id.linearcheck)

        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val db = Firebase.firestore
        val map = hashMapOf<String, String>()
        val array = arrayOf<String>()
        val docRef = db.collection("users")
            .document("$uid")
            .collection("trainings")
        docRef.get()
            .addOnSuccessListener { documents ->

                for (document in documents) {

                    var lp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    val cb = CheckBox(context)

                    ll.addView(cb, lp)
                    //map[document.id] = document.getString("title").toString()
                    //array.plus(document.getString("title").toString())
                    //Log.d("TAG", "${document.id} => ${document.data}")
                    //Log.d("this is my array", "arr: " + Arrays.toString(array))
                    //Log.d("this is my map", map.toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
        //dialog.show(fragmentManager, "customDialog")
        dialog.show()
    }
*/

    private fun openDialog(v: View?, map: LinkedHashMap<String, String>) {
        val array2 = mutableListOf<String>()
        for (v in map){
            array2.add(v.value)
        }
        val array = array2.toTypedArray()
        val newFragment = FireMissilesDialogFragment(array,map)
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