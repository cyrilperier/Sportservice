package com.uqac.pmm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.data.EntrainementDao
import com.uqac.pmm.data.EntrainementDataBase
import com.uqac.pmm.model.Entrainement
import kotlinx.android.synthetic.main.activity_list_entrainement.*
import kotlinx.coroutines.runBlocking

class ListEntrainementActivity :AppCompatActivity() {

    lateinit var entrainements: MutableList<Entrainement>
    lateinit var database: EntrainementDataBase
    lateinit var entrainementDao: EntrainementDao
    val map = linkedMapOf<String, String>()
    val array = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_entrainement)

        list_entrainements_recyclerview.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
    }

    override fun onStart() {


        super.onStart()
        Dao()
        read_entrainement()



    }

    fun read_entrainement() {

        val db = Firebase.firestore
        db.collection("Entrainement")
            .get()
            .addOnSuccessListener { result ->
                for (entrainement in result) {


                    map[entrainement.id] = entrainement.getString("title").toString()
                    array.add(entrainement.getString("title").toString())
                    entrainements.map {
                        Entrainement(
                            1,
                            entrainement.getString("title").toString()
                        )
                    }.map {
                        Log.d("TEST", "avant "+ it.id.toString())
                        runBlocking {
                            try {
                                    Log.d("TEST","it : "+ it.id.toString())
                                val entrainement_database_local = entrainementDao.findByid(it.id)
                                Log.d("TEST", entrainement_database_local.toString())
                            } catch (e: Exception) {
                                entrainementDao.addEntrainement(it)
                            }

                        }
                    }

                    Log.d("TEST", "map "+ map.toString())

                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents.", exception)
            }
        runBlocking {
            entrainements = entrainementDao.getAllEntrainements().toMutableList()
Log.d("TEST","local "+ entrainements.toString())
        list_entrainements_recyclerview.adapter =
            ListEntrainementAdapter(entrainements, this@ListEntrainementActivity)
    }
}


    private fun Dao(){
        //accés a la base
        database = Room.databaseBuilder(
            this, EntrainementDataBase::class.java, "entrainements-db"

        ).build()
        entrainementDao = database.getEntrainementDao()
    }
}