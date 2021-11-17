package com.uqac.pmm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.data.SerieDao
import com.uqac.pmm.data.SerieDataBase
import com.uqac.pmm.model.Serie
import kotlinx.android.synthetic.main.activity_list_entrainement.*
import kotlinx.android.synthetic.main.activity_list_serie.*
import kotlinx.android.synthetic.main.fragment_detail_entrainement.*
import kotlinx.coroutines.runBlocking

class FragmentDetailEntrainementActivity(idFirebaseEntrainement:String,idFirebaseExercice:List<String>)  : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ListSerieAdapter.SerieViewHolder>? = null
    lateinit var series: List<Serie>
    lateinit var database: SerieDataBase
    lateinit var serieDao: SerieDao
    var idFirebaseEntrainement=idFirebaseEntrainement
    var idFirebaseExercice=idFirebaseExercice
    var refrech=false

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(
            position: Int,
            idFirebaseEntrainement: String,
            idFirebaseExercice: List<String>
        ): Fragment {
            val detailEntrainementFragment = FragmentDetailEntrainementActivity(idFirebaseEntrainement,idFirebaseExercice)
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            detailEntrainementFragment.arguments = bundle
            Log.d("DETAIL",idFirebaseEntrainement.toString())
            Log.d("DETAIL",idFirebaseExercice.toString())

            return detailEntrainementFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_entrainement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = requireArguments().getInt(ARG_POSITION)
/*
        val EntrainementNamesArray = requireContext().resources.getStringArray(R.array.entrainement_names)


        exerciceDetail.text = EntrainementNamesArray[position]
        */
        Dao()
        read_entrainement()




    }
    fun read_entrainement() {

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val map = linkedMapOf<String, String>()
        val array = mutableListOf<String>()
        Log.d("TEST", "avant " + id)
        for (id in idFirebaseExercice) {
            Log.d("TEST", "avant " + id)

        db.collection("users")
            .document("$uid")
            .collection("trainings")
            .document("$idFirebaseEntrainement ")
            .collection("exercices")
            .document("$id")
            .collection("serie")
            .get()
            .addOnSuccessListener { results ->
                Log.d("TEST", "entrainement " + results)
                series = results.map {
                    Serie(null,it.id,
                        it.get("name").toString(),
                        it.get("poids").toString().toInt(),
                        it.get("repetition").toString().toInt())
                }

                Log.d("TEST", "entrainement " + results.documents)

                series.map {
                    Log.d("TEST", "avant " + it.id.toString())
                    runBlocking {
                        try {
                            Log.d("TEST", "it : " + it.id.toString())
                            val entrainement_database_local = serieDao.findByid(it.idFirebaseExercice)
                            Log.d("TEST", entrainement_database_local.toString())
                        } catch (e: Exception) {
                            serieDao.addSerie(it)
                        }
                    }
                }
            }
        Log.d("TEST", "map "+ map.toString())
        Log.d("TEST", "array "+ array.toString())
        }

        runBlocking {
            series = serieDao.getAllSeries().toMutableList()
            Log.d("TEST","local "+ series.toString())

        }


    }
    private fun Dao(){
        //accés a la base
        database = context?.let {
            Room.databaseBuilder(
                it, SerieDataBase::class.java, "serie-db"

            ).build()
        }!!
        serieDao = database.getSerieDao()
    }

    }
