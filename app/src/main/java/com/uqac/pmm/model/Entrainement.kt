package com.uqac.pmm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entrainements")
data class Entrainement(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val idFirebase:String?,
    val name:String?


){}