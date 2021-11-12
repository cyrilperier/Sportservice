package com.uqac.pmm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.uqac.pmm.ui.main.SectionsPagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*

import com.google.firebase.firestore.ktx.firestore
import java.io.Serializable


class HomeActivity : AppCompatActivity() {

    private lateinit var sectionNamesArray: Array<String>

    private var sectionPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sectionNamesArray = resources.getStringArray(com.uqac.pmm.R.array.section_names)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, sectionNamesArray.size)
        sectionViewPager.adapter = sectionsPagerAdapter

        sectionViewPager.registerOnPageChangeCallback(sectionPageChangeCallback)

        TabLayoutMediator(tabLayout, sectionViewPager) { tab, position ->
            tab.text = sectionNamesArray[position].substringBefore(' ')
        }.attach()
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        Log.d("TAG","token mon boug :  $uid")
        user!!.getIdToken(true).addOnSuccessListener { result ->
            val idToken = result.token
            //Do whatever
            if (idToken != null) {
                Log.d("TAG","token :  $idToken")
            }
        }
        val db = Firebase.firestore
        val training = hashMapOf(
            "title" to "training 1",
            "exercices" to arrayListOf<String>("test5", "test6"),
            "dates" to arrayListOf<String>("23 novembre 2021 à 00:00:00 UTC-5\n"),
        )
        //ADD A TRAINING TO THE USER'S TRAINING COLLECTION
        addTraining(uid,training)

        //permet de get les doc de la collection training
        val docRef = db.collection("users").document("$uid")
            .collection("trainings")
        docRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }

    }

    //ADD A TRAINING TO THE USER'S TRAINING COLLECTION
    private fun addTraining(uid: String?, training: HashMap<String, Serializable>) {

        val db = Firebase.firestore
        db.collection("users").document("$uid")
            .collection("trainings")
            .add(training)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d("TAG", "Error adding document", e)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        sectionViewPager.unregisterOnPageChangeCallback(sectionPageChangeCallback)
    }
}