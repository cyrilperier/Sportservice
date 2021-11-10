package com.uqac.pmm.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.uqac.pmm.FragmentCalandarActivity
import com.uqac.pmm.FragmentMainActivity
import com.uqac.pmm.FragmentProfileActivity
import com.uqac.pmm.R
import com.uqac.pmm.fragment.FirstFragment


private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.Tab_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
       when(position){
           0->{return FragmentMainActivity.newInstance(0,"la")}
           1->{return FragmentProfileActivity.newInstance(1,"la")}
           2->{return FragmentCalandarActivity.newInstance(2,"la")}
           else ->{return FirstFragment.newInstance(0,"la")}

       }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return TAB_TITLES.size
    }
}