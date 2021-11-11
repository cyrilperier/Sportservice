package com.uqac.pmm

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.uqac.pmm.ui.main.SectionsPagerAdapter
import com.uqac.pmm.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab

    }
    fun Layout_Change_Selection(view: View){
        setContentView(R.layout.activity_selection);
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


}