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

class DialogFragmentAddSerie(idFirebaseEntrainement:String,idExercice:String,nbSerie:String)  : DialogFragment(){
    var idFirebaseEntrainement=idFirebaseEntrainement
    var idExercice=idExercice
    var nbSerie=nbSerie
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            var inflater : LayoutInflater = requireActivity().layoutInflater
            var view : View = inflater.inflate(R.layout.add_serie_to_exercice,null)
            var btn : Button = view.findViewById(R.id.add_serie_button)
            var poids : EditText = view.findViewById(R.id.Poids_edit_text)
            val repetition : EditText = view.findViewById(R.id.repetition_edit_text)
            builder.setView(view)

            btn.setOnClickListener {
                addSerieToExercice(poids.text.toString(),repetition.text.toString(),nbSerie)
                dismiss()
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    protected fun addSerieToExercice(poids : String,repetition: String,nbSerie :String) {
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val db = Firebase.firestore
        val serie = hashMapOf(
            "name" to "Serie "+nbSerie,
            "poids" to poids,
            "repetition" to repetition,
        )

        db.collection("users")
            .document("$uid")
            .collection("trainings")
            .document("$idFirebaseEntrainement")
            .collection("exercices")
            .document("$idExercice")
            .collection("serie")
            .add(serie)
            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }

    }
}