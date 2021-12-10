package com.uqac.pmm.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.zip.ZipOutputStream

enum class Type{

    SANSMACHINE,MACHINE,ECHAUFFEMENT,ETIREMENT

}


enum class Zone{

    BACK,BICEPS,TRICEPS,PECTORALS,SHOULDERS,LEGS,ABDOMINALS

}

@Entity(tableName = "exercices")
data class Exercice(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val idFirebaseEntrainement:String?,
    val name:String?,
    val type: Type?,
    val zone: Zone?

){}