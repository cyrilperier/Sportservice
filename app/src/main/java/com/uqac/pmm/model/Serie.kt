package com.uqac.pmm.model

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "series")
data class Serie(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val idFirebaseExercice:String?,
    val name:String?,
    val poids : Int?,
    val repetition : Int?

){}