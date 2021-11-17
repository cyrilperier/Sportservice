package com.uqac.pmm.data

import androidx.room.*
import androidx.room.Database
import androidx.room.RoomDatabase
import com.uqac.pmm.model.Exercice
import androidx.room.TypeConverters
import com.uqac.pmm.model.Serie


@Database(entities = [Serie::class], version = 1)
@TypeConverters(TypeConversion::class)
abstract class SerieDataBase: RoomDatabase(){

    abstract fun getSerieDao() : SerieDao

}

