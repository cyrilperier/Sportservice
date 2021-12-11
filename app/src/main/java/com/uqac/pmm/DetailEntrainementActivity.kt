package com.uqac.pmm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.uqac.pmm.model.Exercice

import com.uqac.pmm.ui.main.SectionsPagerDetailEntrainementAdaptater
import kotlinx.android.synthetic.main.activity_detail_entrainement.*


class DetailEntrainementActivity : AppCompatActivity() {
    lateinit var exercices: List<Exercice>
    private lateinit var detailEntrainementNamesArray: Array<String>
    var array = mutableListOf<String>()
    var arrayid = mutableListOf<String>()
    val list = mutableListOf<String>()
    lateinit var idFirebase: String
    var refrech=false
    private var detailEntrainementPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch to AppTheme for displaying the activity
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_entrainement)
        if (intent.hasExtra("idFirebase")) {
            idFirebase = intent?.getStringExtra("idFirebase")!!}
        //detailEntrainementNamesArray=resources.getStringArray(com.uqac.pmm.R.array.entrainement_names)

        read_entrainement()


    }

    override fun onDestroy() {
        super.onDestroy()
        detailEntrainementViewPager.unregisterOnPageChangeCallback(detailEntrainementPageChangeCallback)
    }

    fun read_entrainement() {

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user?.uid
        val map = linkedMapOf<String, String>()



        db.collection("users").document("$uid")
            .collection("trainings").document("$idFirebase").collection("exercices")
            .get()
            .addOnSuccessListener { result ->
                    array = result.map{it.get("name").toString()} as MutableList<String>
                arrayid = result.map{
                    it.id} as MutableList<String>
                Log.d("DETAIL","array"+array)
                detailEntrainementNamesArray= array.toTypedArray()
                val detailEntrainementAdaptater = SectionsPagerDetailEntrainementAdaptater(this, detailEntrainementNamesArray.size,idFirebase,arrayid)
                detailEntrainementViewPager.adapter = detailEntrainementAdaptater


                detailEntrainementViewPager.registerOnPageChangeCallback(detailEntrainementPageChangeCallback)

                TabLayoutMediator(tabLayoutEntrainement, detailEntrainementViewPager) { tab, position ->

                    tab.text = detailEntrainementNamesArray[position].substringBefore(' ')
                }.attach()
            }


    }
}