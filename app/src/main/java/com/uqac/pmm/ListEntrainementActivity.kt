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
import kotlinx.android.synthetic.main.activity_list_entrainement.*
import kotlinx.android.synthetic.main.list_entrainement_view.*
import kotlinx.coroutines.runBlocking


class ListEntrainementActivity :AppCompatActivity() {

    lateinit var entrainements: List<Entrainement>

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        val v = setContentView(R.layout.activity_list_entrainement)

        list_entrainements_recyclerview.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        addTraining.setOnClickListener {
            confirmFireMissiles()
        }

    }

    override fun onStart() {


        super.onStart()

        read_entrainement()


    }

    fun confirmFireMissiles() {
        val newFragment = DialogFragmentAddTraining()
        supportFragmentManager.let { newFragment.show(it, "missiles") }
    }


    fun read_entrainement() {

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user?.uid


        db.collection("users").document("$uid")
            .collection("trainings")
            .get()
            .addOnSuccessListener { results ->


                entrainements = results.map {
                    Entrainement(null, it.id, it.get("title").toString())
                }

                list_entrainements_recyclerview.adapter =
                    ListEntrainementAdapter(entrainements, this@ListEntrainementActivity)


            }


    }
}