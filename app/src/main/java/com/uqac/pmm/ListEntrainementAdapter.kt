package com.uqac.pmm

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.model.Entrainement
import kotlinx.android.synthetic.main.activity_list_entrainement.view.*
import kotlinx.android.synthetic.main.list_date_view.view.*
import kotlinx.android.synthetic.main.list_entrainement_view.view.*
import java.util.*
import java.util.zip.Inflater


class ListEntrainementAdapter (val entrainements : List<Entrainement>, val context : Context,val commencer:Boolean,val history:Boolean) : RecyclerView.Adapter<com.uqac.pmm.ListEntrainementAdapter.EntrainementViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    class EntrainementViewHolder(val entrainementView: View) : RecyclerView.ViewHolder(entrainementView)

    private var isExpanded = false
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntrainementViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.list_entrainement_view, parent, false)


        return EntrainementViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: EntrainementViewHolder, position: Int)  {
        val entrainement: Entrainement = entrainements[position]
        collapse(holder)
        holder.entrainementView.entrainement_name_textview.text=
            "${entrainement.name} "

        hideButton(holder)


        holder.entrainementView.dropdownButton.setOnClickListener {

                    if (isExpanded) {
                        collapse(holder)
                        hideButton(holder)

                    }else {

                        expand(holder,entrainement)
                        hideButton(holder)
                    }
                }
        holder.entrainementView.setOnClickListener {

            with(it.context) {
                val intent = Intent(this, DetailEntrainementActivity::class.java)
                intent.putExtra("entrainement_name", entrainement.name)
                intent.putExtra("idFirebase", entrainement.idFirebase)
                intent.putExtra("commencer",commencer)
                startActivity(intent)

            }
        }
        holder.entrainementView.addDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                val date = Date(year-1900,monthOfYear,dayOfMonth)
                val db = Firebase.firestore
                val user = Firebase.auth.currentUser
                val uid = user?.uid
                val dates = entrainement.dates
                val ts_date = Timestamp(date)
                dates?.add(ts_date)
                db.collection("users").document("$uid")
                    .collection("trainings")
                    .document(entrainement.idFirebase.toString())
                    .update("dates",dates)
                Log.d("TAG DATE SELECTIONEE", "$dayOfMonth / $month / $year")
                }, year, month, day)
                dpd.show()

        }
        holder.entrainementView.list_dates_recyclerview.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(context)
            // set the custom adapter to the RecyclerView
            adapter = context?.let { ListDatesAdapter(entrainement.dates,entrainement.idFirebase, it) }
        }

        holder.entrainementView.delete_training_imageView.setOnClickListener {
            with(it.context){

                val db = Firebase.firestore
                val user = Firebase.auth.currentUser
                val uid = user?.uid

                db.collection("users").document("$uid")
                    .collection("trainings")
                    .document(entrainement.idFirebase.toString())
                    .delete()


                val intent = Intent(this, ListEntrainementActivity::class.java)
                startActivity(intent)


                    }

            }


        }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = entrainements.size
    private fun expand(holder: EntrainementViewHolder, entrainement : Entrainement)
    {
        AnimationUtils.expand(holder.entrainementView.list_dates_recyclerview)
        AnimationUtils.expand(holder.entrainementView.addDate)
        AnimationUtils.expand(holder.entrainementView.delete_training_imageView)
        isExpanded = true

    }
    private fun collapse(holder: EntrainementViewHolder)
    {
        AnimationUtils.collapse(holder.entrainementView.list_dates_recyclerview)
        AnimationUtils.collapse(holder.entrainementView.addDate)
        AnimationUtils.collapse(holder.entrainementView.delete_training_imageView)
        isExpanded = false
    }

    private fun hideButton(holder: EntrainementViewHolder){if(history) {
        holder.entrainementView.delete_training_imageView.setVisibility(GONE)
        holder.entrainementView.addDate.setVisibility(GONE)

    }
    else{holder.entrainementView.delete_training_imageView.setVisibility(VISIBLE)}}
}
