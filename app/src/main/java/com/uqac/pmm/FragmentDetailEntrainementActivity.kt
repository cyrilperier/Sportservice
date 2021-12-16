package com.uqac.pmm


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.model.Entrainement
import com.uqac.pmm.model.Serie
import kotlinx.android.synthetic.main.activity_list_entrainement.*
import kotlinx.android.synthetic.main.activity_list_serie.*
import kotlin.properties.Delegates


class FragmentDetailEntrainementActivity(idFirebaseEntrainement:String,idFirebaseExercice:List<String>,commencer:Boolean)  : Fragment() {




    lateinit var series: List<Serie>
    var idFirebaseEntrainement=idFirebaseEntrainement
    var idFirebaseExercice=idFirebaseExercice
    var commencer=commencer
    lateinit var nbSerie:String
    lateinit var idExercice : String
    private var terminer_exercice_button: Button? = null
    var nb =0

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(
            position: Int,
            idFirebaseEntrainement: String,
            idFirebaseExercice: List<String>,
            commencer: Boolean
        ): Fragment {
            val detailEntrainementFragment = FragmentDetailEntrainementActivity(idFirebaseEntrainement,idFirebaseExercice,commencer)
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
        var v=inflater.inflate(R.layout.activity_list_serie, container, true)
        terminer_exercice_button=v?.findViewById(R.id.finir_exercice_button)
        if(commencer==false) {
            terminer_exercice_button?.setVisibility(View.INVISIBLE)
        }

        else{terminer_exercice_button?.setVisibility(View.VISIBLE)}
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = requireArguments().getInt(ARG_POSITION)


        idExercice=idFirebaseExercice[position]

        read_entrainement()
Log.d("TEST",position.toString())

        addExercice.setOnClickListener {
            confirmFireMissiles()
        }
        finir_exercice_button.setOnClickListener {
            finirExercice(position+1)
        }

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
               nbSerie= (results.size()+1).toString()
                series = results.map {
                    Serie(null,idFirebaseEntrainement,idExercice,it.id,
                        it.get("name").toString(),
                        it.get("poids").toString().toInt(),
                        it.get("repetition").toString().toInt())
                }

                Log.d("TEST", "serie " + series)



                list_series_recyclerview.apply {
                    // set a LinearLayoutManager to handle Android
                    // RecyclerView behavior
                    layoutManager = LinearLayoutManager(activity)
                    // set the custom adapter to the RecyclerView
                    adapter = context?.let { ListSerieAdapter(series, it) }
                }



            }






    }

    fun confirmFireMissiles() {
        val newFragment = DialogFragmentAddSerie(idFirebaseEntrainement,idExercice,nbSerie)
        fragmentManager.let {
            if (it != null) {
                newFragment.show(it, "missiles")
            }
        }
    }

    fun finirExercice(idExercice:Int){
        nb=idExercice
        Log.d("TEST", "nb:"+nb.toString())
        Log.d("TEST","limite:"+idFirebaseExercice.size.toString())
        if(nb==idFirebaseExercice.size) {
            with(context) {

                get_entrainement()

                val intent = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        finir_exercice_button.setEnabled(false)

    }

    fun get_entrainement() {

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user?.uid


       db.collection("users").document("$uid")
            .collection("trainings")
           .document("$idFirebaseEntrainement")
            .get()
            .addOnSuccessListener { results ->

                var idTraining=results.id
                var nameTraining=results.get("title")

                val entrainement = hashMapOf(
                    "title" to nameTraining
                )

                db.collection("users").document("$uid")
                    .collection("history")
                    .document("4jCppJ7gHeqSbDg69kA7")
                    .collection("trainings")
                    .document("$idTraining")
                    .set(entrainement)

            }




    }

    }
