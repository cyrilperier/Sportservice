package com.uqac.pmm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.data.EntrainementDataBase
import com.uqac.pmm.data.SerieDao
import com.uqac.pmm.data.SerieDataBase
import com.uqac.pmm.model.Entrainement
import com.uqac.pmm.model.Serie
import kotlinx.android.synthetic.main.activity_list_entrainement.*
import kotlinx.android.synthetic.main.activity_list_serie.*
import kotlinx.coroutines.runBlocking

class ListSerieActivity : AppCompatActivity(){

    lateinit var series: List<Serie>
    lateinit var database: SerieDataBase
    lateinit var serieDao: SerieDao
    var refrech=false
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_serie)

        list_series_recyclerview.layoutManager =
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
        runBlocking {
            series = serieDao.getAllSeries().toMutableList()

        }





    }

    fun read_entrainement() {

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val map = linkedMapOf<String, String>()
        val array = mutableListOf<String>()


        db.collection("users").document("$uid")
            .collection("trainings")
            .get()
            .addOnSuccessListener { results ->

                Log.d("TEST", "avant " + results.toString())
                series = results.map {
                    Serie(null,it.id, it.get("title").toString(),it.get("poids").toString().toInt(),it.get("repetition").toString().toInt())
                }
                Log.d("TEST", "entrainement " + series.toString())

                series.map {
                    Log.d("TEST", "avant " + it.id.toString())
                    runBlocking {
                        try {
                            Log.d("TEST", "it : " + it.id.toString())
                            val entrainement_database_local = serieDao.findByid(it.idFirebaseExercice)
                            Log.d("TEST", entrainement_database_local.toString())
                        } catch (e: Exception) {
                            serieDao.addSerie(it)

                            if(refrech==false) {
                                finish();
                                startActivity(getIntent())
                                refrech=true
                            }else{}
                        }

                    }
                }
            }
        Log.d("TEST", "map "+ map.toString())
        Log.d("TEST", "array "+ array.toString())


        runBlocking {
            series = serieDao.getAllSeries().toMutableList()
            Log.d("TEST","local "+ series.toString())
            list_entrainements_recyclerview.adapter =
                ListSerieAdapter(series, this@ListSerieActivity)
        }


    }


    private fun Dao(){
        //accés a la base
        database = Room.databaseBuilder(
            this, SerieDataBase::class.java, "entrainements-db"

        ).build()
        serieDao = database.getSerieDao()
    }


}
