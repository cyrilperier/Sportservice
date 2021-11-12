package com.uqac.pmm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.uqac.pmm.ui.main.SectionsPagerAdapter
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import com.google.firebase.auth.GetTokenResult

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.ktx.firestore


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
        val docRef = db.collection("users").document("$uid")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        sectionViewPager.unregisterOnPageChangeCallback(sectionPageChangeCallback)
    }
}