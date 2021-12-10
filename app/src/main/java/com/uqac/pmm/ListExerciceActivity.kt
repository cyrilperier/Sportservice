package com.uqac.pmm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.data.EntrainementDao
import com.uqac.pmm.data.EntrainementDataBase
import com.uqac.pmm.model.Entrainement
import com.uqac.pmm.model.Exercice
import com.uqac.pmm.model.Type
import com.uqac.pmm.model.Zone
import kotlinx.android.synthetic.main.activity_list_entrainement.*
import kotlinx.android.synthetic.main.activity_list_exercice.*
import kotlinx.android.synthetic.main.list_entrainement_view.*
import kotlinx.coroutines.runBlocking


class ListExerciceActivity :AppCompatActivity() {

    lateinit var exercices: List<Exercice>
lateinit var type: String
lateinit var types :Type
    var refrech = false
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

    fun confirmFireMissiles() {
        val newFragment = DialogFragmentAddTraining()
        supportFragmentManager.let { newFragment.show(it, "missiles") }
    }


    fun read_exercice() {

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val map = linkedMapOf<String, String>()
        val array = mutableListOf<String>()


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
                            Exercice(null, it.id, it.get("name").toString(),types, Zone.ABDOMINALS)
                        }
                        list_exercices_recyclerview.adapter =
                            ListExerciceAdapter(exercices, this@ListExerciceActivity)


                    }




            }







    }


}