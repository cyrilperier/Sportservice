package com.uqac.pmm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.data.SerieDao
import com.uqac.pmm.data.SerieDataBase
import com.uqac.pmm.model.Serie
import kotlinx.android.synthetic.main.activity_list_serie.*
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
    lateinit var idExercice : String
    var cxt = context

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
        idExercice=idFirebaseExercice[position]
        Dao()
        read_entrainement()



/*
        recycler_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = ListSerieAdapter(series,context)
        } */


    }
    fun read_entrainement() {

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val map = linkedMapOf<String, String>()
        val array = mutableListOf<String>()
            Log.d("TEST", "id exercice " + uid)
            Log.d("TEST", "id entrainement " + idFirebaseEntrainement)
            Log.d("TEST", "id exercice " + idExercice)

        db.collection("users")
            .document("$uid")
            .collection("trainings")
            .document("$idFirebaseEntrainement")
            .collection("exercices")
            .document("$idExercice")
            .collection("serie")
            .get()
            .addOnSuccessListener { results ->

                series = results.map {
                    Serie(null,it.id,
                        it.get("name").toString(),
                        it.get("poids").toString().toInt(),
                        it.get("repetition").toString().toInt())
                }

                Log.d("TEST", "serie " + series)


                list_series_recyclerview.adapter =
                    cxt?.let { ListSerieAdapter(series, it) }
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
