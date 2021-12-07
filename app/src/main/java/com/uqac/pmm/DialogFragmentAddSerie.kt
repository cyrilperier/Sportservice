package com.uqac.pmm

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.util.*

class DialogFragmentAddSerie  : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            var inflater : LayoutInflater = requireActivity().layoutInflater
            var view : View = inflater.inflate(R.layout.add_training_to_user,null)

            var text : EditText = view.findViewById(R.id.trainingTitle)
            val c = Calendar.getInstance()


            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->


                        addTrainingToUser(text.text.toString())
                    })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    protected fun addTrainingToUser(text : String) {
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val db = Firebase.firestore
        val training = hashMapOf(
            "title" to text,
            
        )

        db.collection("users").document("$uid").collection("trainings")
            .add(training)
            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }

    }
}