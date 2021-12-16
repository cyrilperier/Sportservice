package com.uqac.pmm

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.model.Serie
import kotlinx.android.synthetic.main.activity_list_serie.*
import kotlinx.android.synthetic.main.list_entrainement_view.view.entrainement_name_textview
import kotlinx.android.synthetic.main.list_serie_view.view.*


class ListSerieAdapter(val series : List<Serie>, val context : Context) : RecyclerView.Adapter<ListSerieAdapter.SerieViewHolder>()
{


        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        class SerieViewHolder(val serieView: View) : RecyclerView.ViewHolder(serieView)


        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
            val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
            val view: View = layoutInflater.inflate(R.layout.list_serie_view, parent, false)

            return SerieViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)

        override fun onBindViewHolder(holder: SerieViewHolder, position: Int)  {
            val serie: Serie= series[position]
            holder.serieView.serie_name_textview.text=
                "${serie.name} "
            holder.serieView.serie_poids_textview.setText("${serie.poids}")
            holder.serieView.serie_repetition_textview.setText("${serie.repetition}")



holder.serieView.serie_valider_button.setOnClickListener {
    var poids = holder.serieView.serie_poids_textview.text.toString().toInt()
    var repetition = holder.serieView.serie_repetition_textview.text.toString().toInt()
    modify_serie(serie.idFirebaseEntrainement,serie.idFirebaseExercice,serie.idFirebaseSerie,poids,repetition)
    holder.serieView.serie_valider_button.setEnabled(false)

}


        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = series.size

    fun modify_serie(
        idFirebaseEntrainement:String?,
        idExercice:String?,
        idSerie:String?,
        poids: Int?,
        repetition:Int?)  {
        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        db.collection("users")
            .document("$uid")
            .collection("trainings")
            .document("$idFirebaseEntrainement")
            .collection("exercices")
            .document("$idExercice")
            .collection("serie")
            .document("$idSerie")
            .update("poids", poids,"repetition",repetition)

        }


    }