package com.uqac.pmm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.uqac.pmm.ui.main.SectionsPagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*


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

    }

    override fun onDestroy() {
        super.onDestroy()
        sectionViewPager.unregisterOnPageChangeCallback(sectionPageChangeCallback)
    }

        fun Layout_Change_Selection(view: View){
            setContentView(R.layout.fragment_chronometre);
        }

        fun Layout_Change_Workout(view: View){
            setContentView(R.layout.activity_workout);
        }

        fun Layout_Change_Machine(view: View){
            setContentView(R.layout.activity_machine);
        }

        fun Layout_Change_Workout_i(view: View){
            setContentView(R.layout.activity_workout_i);
        }

        fun Layout_Change_Machine_Shoulder_List(view: View){
            setContentView(R.layout.activity_machine_selection);
        }

        fun Layout_Change_Machine_Shoulder_description(view: View){
            setContentView(R.layout.activity_machine_description);
        }

    }
