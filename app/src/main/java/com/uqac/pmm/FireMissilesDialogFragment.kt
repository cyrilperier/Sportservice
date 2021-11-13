package com.uqac.pmm

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable
import kotlin.collections.ArrayList

import kotlin.collections.HashMap


class FireMissilesDialogFragment(array: Array<String>, map: LinkedHashMap<String, String>) : DialogFragment(){
    val array = array
    val map = map
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val selectedItems = ArrayList<Int>() // Where we track the selected items
            val builder = AlertDialog.Builder(it)
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
        val exercise = "pompe"
        for (i in selectedItems){
            val tid = map.keys.toTypedArray()[i]
            db.collection("users").document("$uid")
                .collection("trainings").document("$tid")
                .update("exercices", FieldValue.arrayUnion("$exercise"))
        }

    }
}