package com.uqac.pmm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_detail_exercice.*

class DetailExerciceActivity : AppCompatActivity() {

    private var pompes: FloatingActionButton? = null
    lateinit var favori_url_image:String
    lateinit var id:String
    lateinit var name:String
    lateinit var zone:String
    lateinit var description:String
    var fm: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_detail_exercice)


        val intent = intent

        if (intent.hasExtra("exercice_name")) {
            name = intent.getStringExtra("exercice_name").toString()
            id = intent.getStringExtra("exercice_id").toString()
            zone= intent.getStringExtra("exercice_zone").toString()
            favori_url_image=intent.getStringExtra("exercice_image").toString()
            description=intent.getStringExtra("exercice_description").toString()
        }
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
        pompes = findViewById(R.id.addToTraining)
        pompes?.setOnClickListener {
            openDialog(map)
            Log.d("TAG", "t'es bien ici")

        }
        AffichePicture(favori_url_image)
        AfficheInformation(name,description,zone)
    }


    private fun AffichePicture(url_picture: String){

        Glide.with(baseContext)
            .load(url_picture)
            .centerInside()
            .into(exercice_imageView)
    }

    private fun AfficheInformation(name:String,description:String,zone:String){
        description_exercice_textView.text=description
        name_exercice_textView.text=name
        zone_exercice_textView.text=zone

    }
    private fun openDialog(map: LinkedHashMap<String, String>) {
        val array2 = mutableListOf<String>()
        for (a in map) {
            array2.add(a.value)
        }
        val array = array2.toTypedArray()
        val newFragment = FireMissilesDialogFragmentAddTraining(array, map,id,name)

        fragmentManager?.let { newFragment.show(fm, "missiles") }
    }
}


