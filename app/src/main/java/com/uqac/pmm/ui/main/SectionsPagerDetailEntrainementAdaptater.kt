package com.uqac.pmm.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uqac.pmm.FragmentDetailEntrainementActivity

class SectionsPagerDetailEntrainementAdaptater (activity: AppCompatActivity, private val itemsCount: Int,idFirebaseEntrainement:String,idFirebaseExercice:List<String>,commencer:Boolean) :
    FragmentStateAdapter(activity) {
    var idFirebaseEntrainement =idFirebaseEntrainement
    var idFirebaseExercice = idFirebaseExercice
    var commencer= commencer

    override fun getItemCount(): Int {
        return itemsCount
    }


    override fun createFragment(position: Int): Fragment {


        return FragmentDetailEntrainementActivity.getInstance(position,idFirebaseEntrainement,idFirebaseExercice,commencer)

    }
}