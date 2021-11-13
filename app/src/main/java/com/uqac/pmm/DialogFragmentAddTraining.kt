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
import java.io.Serializable
import java.sql.Time
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap


class DialogFragmentAddTraining() : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            var inflater : LayoutInflater = requireActivity().layoutInflater
            var view : View = inflater.inflate(R.layout.add_training_to_user,null)
            var btn : Button = view.findViewById(R.id.pickDateBtn)
            var text : EditText = view.findViewById(R.id.trainingTitle)
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            var selectedDay : Int = day
            var selectedMonth : Int = month
            var selectedYear : Int = year
            btn.setOnClickListener {
                val dpd = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    selectedDay = dayOfMonth
                    selectedMonth = monthOfYear
                    selectedYear = year
                    Log.d("TAG DATE SELECTIONEE", "$dayOfMonth / $month / $year")
                }, year, month, day)
                dpd.show()
            }
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        var date : Date = Date(selectedDay,selectedDay,selectedDay)
                        Log.d("Training name and date", "${text.text} on date : $selectedDay / $selectedMonth / $selectedYear")


                        //TODO addTrainingToUser(text,timestamp_date)


                    })
                    /*
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog().cancel()
                    })*/
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    /*
    protected fun addTrainingToUser(selectedItems: ArrayList<Int>) {
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
    }*/
}