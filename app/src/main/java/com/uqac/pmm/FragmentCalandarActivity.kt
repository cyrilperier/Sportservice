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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FragmentCalandarActivity : Fragment() {
    private var pompes: Button? = null
    private var addtraining: Button? = null
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
        val v = inflater.inflate(R.layout.fragment_activity_calendar, container, false)
        val handler = Handler()
// Define the code block to be executed
// Define the code block to be executed
        val runnableCode: Runnable = object : Runnable {
            override fun run() {
                // Do something here on the main thread
                loadTrainings()
                Log.d("Handlers", "Called on main thread")
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 1000)
            }
        }
// Start the initial runnable task by posting through the handler
// Start the initial runnable task by posting through the handler
        handler.post(runnableCode)
        loadTrainings()
        pompes = v.findViewById(R.id.button_pompes)
        pompes?.setOnClickListener {
            openDialog(v,map)
            Log.d("TAG","t'es bien ici")

        }

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
    fun loadTrainings() {
        val db = Firebase.firestore
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
    }



}