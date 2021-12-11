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
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList


class DialogFragmentAddTraining() : DialogFragment() {
    val map = linkedMapOf<String, String>()
    var selectedItems = ArrayList<Int>()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it,R.style.MyDialogTheme)
            // Get the layout inflater
            var inflater : LayoutInflater = requireActivity().layoutInflater
            var view : View = inflater.inflate(R.layout.add_training_to_user,null)
            var btn : Button = view.findViewById(R.id.pickDateBtn)
            var btn2 : Button = view.findViewById(R.id.pickExercices)
            var text : EditText = view.findViewById(R.id.trainingTitle)
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            var selectedDay : Int = day
            var selectedMonth : Int = month
            var selectedYear : Int = year

            loadExercices()
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
            btn2.setOnClickListener {
                openDialog(requireActivity(),map)
            }
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->

                        Log.d("Training name and date", "${text.text} on date : $selectedDay / $selectedMonth / $selectedYear")
                        var timestamp_date = Timestamp(selectedYear-1900,selectedMonth,selectedDay,0,0,0,0)
                        Log.d("Timestamp date", timestamp_date.toString())
                        addTrainingToUser(text.text.toString(),timestamp_date,selectedItems)
                    })
                    /*
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog().cancel()
                    })*/
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    protected fun addTrainingToUser(text: String, date: Timestamp, selectedItems: ArrayList<Int>) {
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val db = Firebase.firestore
        Log.d("DATE AVANT PUSH",date.toString())
        val dates = ArrayList<Timestamp>()
        dates.add(date)
        val training = hashMapOf(
            "title" to text,
            "dates" to dates
        )
        val newExercises = ArrayList<Int>()
        db.collection("users").document("$uid").collection("trainings")
            .add(training)
            .addOnSuccessListener { documentReference ->

                for (i in selectedItems){
                    Log.d("SELCTION",i.toString())
                    val key_id = map.keys.toTypedArray()[i]
                    val value_name = map.get(key_id)
                    val exercise = hashMapOf(
                        "name" to value_name
                    )
                    db.collection("users").document("$uid")
                        .collection("trainings").document(documentReference.id)
                        .collection("exercices").document("$key_id")
                        .set(exercise)
                }
            }
            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }

    }
    private fun openDialog(fa: FragmentActivity, map: LinkedHashMap<String, String>) {
        val array = mutableListOf<String>()
        for (v in map){
            array.add(v.value)
        }
        selectedItems.clear()
        val alertDialogBuilder = AlertDialog.Builder(fa, R.style.MyDialogTheme)
        alertDialogBuilder.setTitle(R.string.exercise_picker)
            .setMultiChoiceItems(array.toTypedArray(), null,
                DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                    if (isChecked) {
                        selectedItems.add(which)
                    } else if (selectedItems.contains(which)) {
                        selectedItems.remove(which)
                    }
                })
            // Set the action buttons
            .setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, id ->
                    Log.d("ICIIIII", selectedItems.toString())

                })
            .setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                })
        val alert: AlertDialog = alertDialogBuilder.create()
        alert.show()
        //alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(R.color.colorPrimary)
        //alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(R.color.colorPrimary)
        //alert.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundColor(R.color.colorPrimary)
        //alert.getButton(DialogInterface.BUTTON_NEGATIVE).setBackgroundColor(R.color.colorPrimary)
        /*val newFragment = DialogExerciseSelector(array.toTypedArray(),map,fa)
        newFragment.setTargetFragment(this, 0)
        fragmentManager?.let { newFragment.show(it,"newfrag") }*/
    }

    fun loadExercices() {
        val db = Firebase.firestore
        val docRef = db.collection("exercises")
        docRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    db.collection("exercises").document(document.id).collection("exercises")
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                map[document.id] = document.getString("name").toString()
                            }
                        }
                    //map[document.id] = document.getString("name").toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }
}