package com.uqac.pmm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_exercice.*

class DetailExerciceActivity : AppCompatActivity() {


    lateinit var favori_url_image:String

    lateinit var name:String
    lateinit var zone:String
    lateinit var description:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_detail_exercice)


        val intent = intent

        if (intent.hasExtra("exercice_name")) {
            name = intent.getStringExtra("exercice_name").toString()
            zone= intent.getStringExtra("exercice_zone").toString()
            favori_url_image=intent.getStringExtra("exercice_image").toString()
            description=intent.getStringExtra("exercice_description").toString()
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
}


