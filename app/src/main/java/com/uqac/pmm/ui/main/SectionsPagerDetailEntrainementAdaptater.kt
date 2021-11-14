package com.uqac.pmm.ui.main

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uqac.pmm.FragmentDetailEntrainementActivity

class SectionsPagerDetailEntrainementAdaptater (activity: AppCompatActivity, private val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {

        return FragmentDetailEntrainementActivity.getInstance(position)
    }
}