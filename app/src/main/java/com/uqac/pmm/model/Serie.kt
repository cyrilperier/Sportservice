package com.uqac.pmm.model

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "series")
data class Serie(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val idFirebaseEntrainement: String?,
    val idFirebaseExercice:String?,
    val idFirebaseSerie: String?,
    val name:String?,
    val poids : Int?,
    val repetition : Int?

){}