package com.uqac.pmm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.uqac.pmm.ui.main.SectionsPagerDetailEntrainementAdaptater
import kotlinx.android.synthetic.main.activity_detail_entrainement.*

class DetailEntrainementActivity : AppCompatActivity() {

    private lateinit var detailEntrainementNamesArray: Array<String>

    private var detailEntrainementPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch to AppTheme for displaying the activity
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_entrainement)

        detailEntrainementNamesArray = resources.getStringArray(R.array.entrainement_names)


        val detailEntrainementAdaptater = SectionsPagerDetailEntrainementAdaptater(this, detailEntrainementNamesArray.size)
        detailEntrainementViewPager.adapter = detailEntrainementAdaptater


        detailEntrainementViewPager.registerOnPageChangeCallback(detailEntrainementPageChangeCallback)

        TabLayoutMediator(tabLayout, detailEntrainementViewPager) { tab, position ->
            //To get the first name of doppelganger celebrities
            tab.text = detailEntrainementNamesArray[position].substringBefore(' ')
        }.attach()


    }

    override fun onDestroy() {
        super.onDestroy()
        detailEntrainementViewPager.unregisterOnPageChangeCallback(detailEntrainementPageChangeCallback)
    }
}