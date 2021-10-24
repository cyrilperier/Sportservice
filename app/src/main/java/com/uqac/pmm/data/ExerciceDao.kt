package com.uqac.pmm.data


import androidx.room.*
import com.uqac.pmm.model.Exercice


@Dao
interface ExerciceDao {

    @Query("select * from exercices")
    suspend fun getAllExercices(): List<Exercice>

    @Query("SELECT * FROM exercices WHERE type IN (:Type)")
    suspend fun getAllExercicesOfType(Type: String): List<Exercice>

    @Query("SELECT * FROM exercices WHERE name LIKE :name  LIMIT 1")
    suspend fun findByName(name: String): Exercice

    @Query("SELECT * FROM exercices WHERE id LIKE :id  LIMIT 1")
    suspend fun findByid(id: Int?): Exercice


    @Insert
    suspend fun addExercice(`exercice`: Exercice): Long

    @Delete
    suspend fun deleteExercice(`exercice`: Exercice)

    @Update
    suspend fun changeByName(`exercice`: Exercice)
}
