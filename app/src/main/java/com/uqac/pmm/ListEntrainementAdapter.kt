package com.uqac.pmm

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uqac.pmm.model.Entrainement
import kotlinx.android.synthetic.main.list_entrainement_view.view.*


class ListEntrainementAdapter (val entrainements : List<Entrainement>, val context : Context) : RecyclerView.Adapter<ListEntrainementAdapter.EntrainementViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class EntrainementViewHolder(val entrainementView: View) : RecyclerView.ViewHolder(entrainementView)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntrainementViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.list_entrainement_view, parent, false)

        return EntrainementViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: EntrainementViewHolder, position: Int)  {
        val entrainement: Entrainement = entrainements[position]
        holder.entrainementView.entrainement_name_textview.text=
            "${entrainement.name} "


        holder.entrainementView.setOnClickListener{

            with(it.context){
                val intent = Intent(this, DetailEntrainementActivity::class.java)
                intent.putExtra("entrainement_name",entrainement.name )
                intent.putExtra("idFirebase",entrainement.idFirebase)
                startActivity(intent)

            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = entrainements.size

}
