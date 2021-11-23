package com.uqac.pmm

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
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
    lateinit var database: EntrainementDataBase
    lateinit var entrainementDao: EntrainementDao
    var refrech=false
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
        Dao()
        read_entrainement()
        runBlocking {
            entrainements = entrainementDao.getAllEntrainements().toMutableList()

        }

    }

    fun confirmFireMissiles() {
        val newFragment = DialogFragmentAddTraining()
        supportFragmentManager.let { newFragment.show(it, "missiles") }
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


                entrainements = results.map {
                    Entrainement(null,it.id, it.get("title").toString())
                }


                entrainements.map {

                    runBlocking {
                        try {

                            val entrainement_database_local = entrainementDao.findByid(it.idFirebase)
                            Log.d("TEST", entrainement_database_local.toString())
                        } catch (e: Exception) {
                            entrainementDao.addEntrainement(it)

                            if(refrech==false) {
                                finish();
                                startActivity(getIntent())
                                refrech=true
                            }else{}
                        }

                    }
                }
            }



        runBlocking {
            entrainements = entrainementDao.getAllEntrainements().toMutableList()

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