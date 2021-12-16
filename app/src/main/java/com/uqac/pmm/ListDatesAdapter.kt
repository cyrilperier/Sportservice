package com.uqac.pmm

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.list_date_view.view.*
import kotlinx.android.synthetic.main.list_serie_view.view.*
import kotlinx.android.synthetic.main.list_serie_view.view.serie_name_textview
import java.util.*
import kotlin.collections.ArrayList


class ListDatesAdapter(val dates: ArrayList<Timestamp>?, val idTraining: String?, val context: Context) : RecyclerView.Adapter<ListDatesAdapter.DatesViewHolder>()
{


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class DatesViewHolder(val dateView: View) : RecyclerView.ViewHolder(dateView)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatesViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.list_date_view, parent, false)

        return DatesViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: DatesViewHolder, position: Int)  {
        val date: Timestamp= dates!![position]
        val textDate = Date(date.seconds*1000)
        val idTraining = idTraining!!
        holder.dateView.serie_name_textview.text= textDate.toString()


        holder.dateView.remove_date.setOnClickListener {
            dates.remove(date)

            with(it.context){

                val db = Firebase.firestore
                val user = Firebase.auth.currentUser
                val uid = user?.uid

                db.collection("users").document("$uid")
                    .collection("trainings")
                    .document(idTraining)
                    .update("dates", dates)
            }
            val intent = Intent(context, ListEntrainementActivity::class.java)
            startActivity(context,intent, null)

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dates!!.size

}