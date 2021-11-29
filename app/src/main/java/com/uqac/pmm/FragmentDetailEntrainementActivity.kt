package com.uqac.pmm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.data.SerieDao
import com.uqac.pmm.data.SerieDataBase
import com.uqac.pmm.model.Serie
import kotlinx.android.synthetic.main.activity_list_serie.*


class FragmentDetailEntrainementActivity(idFirebaseEntrainement:String,idFirebaseExercice:List<String>)  : Fragment() {




    lateinit var series: List<Serie>
    lateinit var database: SerieDataBase
    lateinit var serieDao: SerieDao
    var idFirebaseEntrainement=idFirebaseEntrainement
    var idFirebaseExercice=idFirebaseExercice
    lateinit var idExercice : String


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
        return inflater.inflate(R.layout.activity_list_serie, container, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = requireArguments().getInt(ARG_POSITION)


        idExercice=idFirebaseExercice[position]

        Dao()
        read_entrainement()
Log.d("TEST",position.toString())





    }
    fun read_entrainement() {

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user?.uid
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
                    Serie(null,idFirebaseEntrainement,idExercice,it.id,
                        it.get("name").toString(),
                        it.get("poids").toString().toInt(),
                        it.get("repetition").toString().toInt())
                }

                Log.d("TEST", "serie " + series)

               // list_series_recyclerview.adapter = ListSerieAdapter(series, getActivity())

                list_series_recyclerview.apply {
                    // set a LinearLayoutManager to handle Android
                    // RecyclerView behavior
                    layoutManager = LinearLayoutManager(activity)
                    // set the custom adapter to the RecyclerView
                    adapter = context?.let { ListSerieAdapter(series, it) }
                }

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
