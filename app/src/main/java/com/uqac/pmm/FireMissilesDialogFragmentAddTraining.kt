package com.uqac.pmm

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList


class FireMissilesDialogFragmentAddTraining(
    array: Array<String>,
    map: LinkedHashMap<String, String>,
    id: String,
    name: String
) : DialogFragment(){
    var array = array
    var map = map
    var array2 = mutableListOf<String>()
    var name = name
    var id = id
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val selectedItems = ArrayList<Int>() // Where we track the selected items
            val builder = AlertDialog.Builder(it)
/*
                val db = Firebase.firestore
                val map2 = linkedMapOf<String, String>()
                val user = Firebase.auth.currentUser
                val uid = user?.uid
                val docRef = db.collection("users").document("$uid")
                    .collection("trainings")
                docRef.get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            map2[document.id] = document.getString("title").toString()
                            array2.add(document.getString("title").toString())
                            Log.d("TAG", "${document.id} => ${document.data}")
                            //Log.d("this is my array", "arr: " + Arrays.toString(array))
                            Log.d("this is my map", map2.toString())
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("TAG", "get failed with ", exception)
                    }
*/
            builder.setTitle("Select a training")
                .setMultiChoiceItems(array, null,
                    DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectedItems.add(which)
                        } else if (selectedItems.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            selectedItems.remove(which)
                        }
                    })
                // Set the action buttons
                    .setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        Log.d("What's checked", selectedItems.toString())
                        addToTraining(selectedItems)
                    })/*
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        ...
                    })*/

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    protected fun addToTraining(selectedItems: ArrayList<Int>) {
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val db = Firebase.firestore

        for (i in selectedItems){
            val tid = map.keys.toTypedArray()[i]
            val exercise = hashMapOf(
                "name" to name
            )
            db.collection("users").document("$uid")
                .collection("trainings").document("$tid")
                .collection("exercices").document("$id")
                .set(exercise)
        }
    }

}