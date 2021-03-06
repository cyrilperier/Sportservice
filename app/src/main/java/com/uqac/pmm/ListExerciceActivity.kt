package com.uqac.pmm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.model.Exercice
import com.uqac.pmm.model.Type
import kotlinx.android.synthetic.main.activity_list_entrainement.*
import kotlinx.android.synthetic.main.activity_list_exercice.*
import kotlinx.android.synthetic.main.list_entrainement_view.*

class ListExerciceActivity :AppCompatActivity() {

    lateinit var exercices: List<Exercice>
    lateinit var type: String
    lateinit var types :Type

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        val v = setContentView(R.layout.activity_list_exercice)
        list_exercices_recyclerview.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )

        type = intent?.getStringExtra("type").toString()

       types = (
                when (type) {
                    "SANSMACHINE"-> Type.SANSMACHINE
                    "MACHINE" -> Type.MACHINE
                    "ECHAUFFEMENT" -> Type.ECHAUFFEMENT
                    "ETIREMENT" -> Type.ETIREMENT
                    else -> Type.SANSMACHINE
                })

    }

    override fun onStart() {
        super.onStart()
        read_exercice()
    }

    fun read_exercice() {

        val db = Firebase.firestore

        db.collection("exercises")
            .whereEqualTo("type", "$type")
            .get()
            .addOnSuccessListener { results ->
                var idTypeFirebase = results.map{it.id}

                db.collection("exercises")
                    .document(idTypeFirebase[0])
                    .collection("exercises")
                    .get()
                    .addOnSuccessListener { results ->

                        exercices = results.map {

                            Exercice(null, it.id, it.get("name").toString(),types, it.get("zone").toString(),it.get("url").toString(),it.get("description").toString())
                        }
                        list_exercices_recyclerview.adapter =
                            ListExerciceAdapter(exercices, this@ListExerciceActivity)
                    }
            }
    }
}