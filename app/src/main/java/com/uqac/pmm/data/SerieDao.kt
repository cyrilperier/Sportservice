package com.uqac.pmm.data

import androidx.room.*
import com.uqac.pmm.model.Serie


@Dao
interface SerieDao {

    @Query("select * from series")
    suspend fun getAllSeries(): List<Serie>


    @Query("SELECT * FROM series WHERE name LIKE :name  LIMIT 1")
    suspend fun findByName(name: String): Serie

    @Query("SELECT * FROM series WHERE idFirebaseExercice LIKE :id  LIMIT 1")
    suspend fun findByid(id: String?): Serie


    @Insert
    suspend fun addSerie(`serie`: Serie): Long

    @Delete
    suspend fun deleteSerie(`serie`: Serie)

    @Update
    suspend fun changeByName(`serie`: Serie)
}
