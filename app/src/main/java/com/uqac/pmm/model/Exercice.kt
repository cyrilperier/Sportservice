package com.uqac.pmm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class Type{

    BACK,BICEPS,TRICEPS,PECTORALS,SHOULDERS,LEGS,ABDOMINALS

}

@Entity(tableName = "exercices")
data class Exercice(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name:String,
    val type: Type



){}