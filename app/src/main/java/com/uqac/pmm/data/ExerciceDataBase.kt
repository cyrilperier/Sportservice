

package com.uqac.pmm.data


import androidx.room.*
import com.uqac.pmm.model.Exercice


@Database(entities = [Exercice::class], version = 1)
abstract class ExerciceDataBase: RoomDatabase(){

abstract fun getExerciceDao() : ExerciceDao

        }

