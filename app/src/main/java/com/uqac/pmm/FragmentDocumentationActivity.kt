package com.uqac.pmm


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.model.Type


class FragmentDocumentationActivity : Fragment() {
    private var pompes: Button? = null
    private var button_entrainement: Button? = null
    var button_exercice_sans_machine: Button? = null
    var button_exercice_machine: Button? = null
    var button_exercice_etirement: Button?= null
    var button_exercice_echauffement: Button?=null

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val doppelgangerFragment = FragmentDocumentationActivity()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            doppelgangerFragment.arguments = bundle
            return doppelgangerFragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_activity_documentation, container, false)
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val db = Firebase.firestore
        val map = linkedMapOf<String, String>()
        val array = mutableListOf<String>()
        val docRef = db.collection("users").document("$uid")
            .collection("trainings")
        docRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    map[document.id] = document.getString("title").toString()
                    array.add(document.getString("title").toString())
                    Log.d("TAG", "${document.id} => ${document.data}")
                    //Log.d("this is my array", "arr: " + Arrays.toString(array))
                    Log.d("this is my map", map.toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
        pompes = v.findViewById(R.id.button_pompes)
        pompes?.setOnClickListener {
            openDialog(v, map)
            Log.d("TAG", "t'es bien ici")

        }


        openExerciceSansMachine(v)
        openEntrainement(v)
        openExerciceMachine(v)
        openExerciceEtirement(v)
        openExerciceEcahuffement(v)



        return v
    }

    private fun openDialog(v: View?, map: LinkedHashMap<String, String>) {
        val array2 = mutableListOf<String>()
        for (v in map) {
            array2.add(v.value)
        }
        val array = array2.toTypedArray()
        val newFragment = FireMissilesDialogFragmentAddTraining(array, map)
        fragmentManager?.let { newFragment.show(it, "missiles") }
    }

    fun openExerciceSansMachine(v: View?) {

        button_exercice_sans_machine = v?.findViewById(R.id.button_exercice_sans_machine)
        button_exercice_sans_machine?.setOnClickListener {


            with(it.context) {
                val intent = Intent(this, com.uqac.pmm.ListExerciceActivity::class.java)
                    .putExtra("type", "SANSMACHINE")
                startActivity(intent)
            }
        }

    }

    fun openEntrainement(v: View?){
        button_entrainement = v?.findViewById(R.id.button_entrainement)
        button_entrainement?.setOnClickListener {

            with(it.context) {
                val intent = Intent(this, ListEntrainementActivity::class.java)
                startActivity(intent)

            }
        }

    }

    fun openExerciceMachine(v:View?){
        button_exercice_machine = v?.findViewById(R.id.button_machines)
        button_exercice_machine?.setOnClickListener {


            with(it.context) {
                val intent = Intent(this, com.uqac.pmm.ListExerciceActivity::class.java)
                    .putExtra("type", "MACHINE")
                startActivity(intent)
            }
        }

    }

    fun openExerciceEtirement(v:View?){
        button_exercice_etirement = v?.findViewById(R.id.button_etirement)
        button_exercice_etirement?.setOnClickListener {


            with(it.context) {
                val intent = Intent(this, com.uqac.pmm.ListExerciceActivity::class.java)
                    .putExtra("type", "ETIREMENT")
                startActivity(intent)
            }
        }

    }

    fun openExerciceEcahuffement(v:View?){
        button_exercice_echauffement = v?.findViewById(R.id.button_echauffement)
        button_exercice_echauffement?.setOnClickListener {


            with(it.context) {
                val intent = Intent(this, com.uqac.pmm.ListExerciceActivity::class.java)
                    .putExtra("type", "ECHAUFFEMENT")
                startActivity(intent)
            }
        }

    }


}