package com.uqac.pmm.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp

@Entity(tableName = "entrainements")
data class Entrainement(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val idFirebase:String?,
    val name:String?,
    val dates:ArrayList<Timestamp>?


)