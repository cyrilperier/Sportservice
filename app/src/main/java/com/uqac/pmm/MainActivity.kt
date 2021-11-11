package com.uqac.pmm

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uqac.pmm.fragments.CalendarFragment
import com.uqac.pmm.fragments.DocFragment
import com.uqac.pmm.fragments.ProfileFragment
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private var myViewPagerAdapter: ScreenSlidePagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_exercice()
        this.deleteDatabase ("exercices-db") //Ne pas oublier de supprimer
        read_exercice()
        Log.d("TAG","test")
        viewPager = findViewById(R.id.pager)
        myViewPagerAdapter = ScreenSlidePagerAdapter(
            supportFragmentManager
        )
        myViewPagerAdapter!!.addFragment(DocFragment())
        myViewPagerAdapter!!.addFragment(ProfileFragment())
        myViewPagerAdapter!!.addFragment(CalendarFragment())
        viewPager.adapter = myViewPagerAdapter
    }
    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }
    class ScreenSlidePagerAdapter(fa: FragmentManager) : FragmentPagerAdapter(fa) {
        private val fragments: ArrayList<Fragment> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }
        override fun getCount(): Int {
            return fragments.size
        }
        fun addFragment(fragment: Fragment) {
            fragments.add(fragment)
        }
    }


    fun add_exercice(){
        Log.d("TAG","add exercice")
        val db = Firebase.firestore

        val exercice = hashMapOf(
            "Nom" to "burpees",
            "Type" to "sans machine",
            "Zone" to "corps"
        )

// Add a new document with a generated ID
        db.collection("Exercice")
            .add(exercice)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d("TAG", "Error adding document", e)
            }

    }
    fun read_exercice() {
        Log.d("TAG","read exercice")
        val db = Firebase.firestore
        db.collection("Exercice")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents.", exception)
            }
    }
}