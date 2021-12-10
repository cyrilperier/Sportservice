package com.uqac.pmm.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.zip.ZipOutputStream

enum class Type{

    SANSMACHINE,MACHINE,ECHAUFFEMENT,ETIREMENT

}




@Entity(tableName = "exercices")
data class Exercice(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val idFirebaseEntrainement:String?,
    val name:String?,
    val type: Type?,
    val zone: String,
    val url_image:String?,
    val description:String?

){}