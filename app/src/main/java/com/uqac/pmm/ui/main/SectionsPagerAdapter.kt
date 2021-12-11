package com.uqac.pmm.ui.main


import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uqac.pmm.FragmentCalendarActivity
import com.uqac.pmm.FragmentDocumentationActivity
import com.uqac.pmm.FragmentProfileActivity



class SectionsPagerAdapter(activity: AppCompatActivity, private val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("TAG", position.toString())
            when (position) {
                0 -> {
                    return FragmentProfileActivity.getInstance(position)
                }
                1 -> {
                    return FragmentDocumentationActivity.getInstance(position)
                }
                2 -> {
                    return FragmentCalendarActivity.getInstance(position)
                }
                else -> {
                    return FragmentDocumentationActivity.getInstance(position)
                }
            }
        }
    }
