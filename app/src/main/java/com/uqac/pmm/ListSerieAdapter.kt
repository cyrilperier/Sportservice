package com.uqac.pmm

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uqac.pmm.model.Serie
import kotlinx.android.synthetic.main.list_entrainement_view.view.*


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
            val view: View = layoutInflater.inflate(R.layout.list_entrainement_view, parent, false)

            return SerieViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: SerieViewHolder, position: Int)  {
            val serie: Serie= series[position]
            holder.serieView.entrainement_name_textview.text=
                "${serie.name} "

        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = series.size

    }