package com.uqac.pmm

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uqac.pmm.model.Exercice
import com.uqac.pmm.model.Type
import kotlinx.android.synthetic.main.list_entrainement_view.view.*
import kotlinx.android.synthetic.main.list_entrainement_view.view.entrainement_name_textview
import kotlinx.android.synthetic.main.list_exercice_view.view.*


class ListExerciceAdapter (val exercices : List<Exercice>, val context : Context) : RecyclerView.Adapter<ListExerciceAdapter.ExerciceViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ExerciceViewHolder(val exerciceView: View) : RecyclerView.ViewHolder(exerciceView)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.list_exercice_view, parent, false)

        return ExerciceViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ExerciceViewHolder, position: Int)  {
        val exercice: Exercice = exercices[position]
        holder.exerciceView.exercice_name_textview.text=
            "${exercice.name} "


        holder.exerciceView.setOnClickListener{

            with(it.context){

                var type= (
                        when (exercice.type) {
                            Type.SANSMACHINE-> "SANSMACHINE"
                            Type.MACHINE-> "MACHINE"
                            Type.ECHAUFFEMENT->  "ECHAUFFEMENT"
                            Type.ETIREMENT -> "ETIREMENT"
                            else -> "SANSMACHINE"
                        })

                val intent = Intent(this, DetailExerciceActivity::class.java)
                intent.putExtra("exercice_name",exercice.name )
               intent.putExtra("exercice_type",type)
                intent.putExtra("exercice_image",exercice.url_image)
                intent.putExtra("exercice_description",exercice.description)
                intent.putExtra("exercice_zone",exercice.zone)
                startActivity(intent)

            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = exercices.size

}
