package com.uqac.pmm.data

import androidx.room.*
import com.uqac.pmm.model.Entrainement
import com.uqac.pmm.model.Exercice


@Dao
interface EntrainementDao {

    @Query("select * from entrainements")
    suspend fun getAllEntrainements(): List<Entrainement>

    @Query("SELECT * FROM entrainements WHERE name LIKE :name  LIMIT 1")
    suspend fun findByName(name: String): Entrainement

    @Query("SELECT * FROM entrainements WHERE idFirebase LIKE :id  LIMIT 1")
    suspend fun findByid(id: String?): Entrainement


    @Insert
    suspend fun addEntrainement(`entrainement`: Entrainement): Long

    @Delete
    suspend fun deleteEntrainement(`entrainement`: Entrainement)

    @Update
    suspend fun changeByName(`entrainement`: Entrainement)
}