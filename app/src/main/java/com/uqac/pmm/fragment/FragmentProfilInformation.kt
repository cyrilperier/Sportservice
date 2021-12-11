package com.uqac.pmm.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.R
import kotlinx.android.synthetic.main.fragment_profil_information.*


class FragmentProfilInformation : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        read_user()

        return inflater.inflate(R.layout.fragment_profil_information, container, false)
    }


    fun read_user() {

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user?.uid


        db.collection("users").document("$uid")
            .get()
            .addOnSuccessListener { results ->


                    poids_textView.text = results.get("weight").toString() + " kg"
                    taille_textView.text = results.get("height").toString() + " cm"
                    age_textView.text = results.get("age").toString() + " ans"
                    nom_textView.text = results.get("lastname").toString()
                    prenom_textView.text = results.get("firstname").toString()
            }
    }
}