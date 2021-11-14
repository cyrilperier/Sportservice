package com.uqac.pmm.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.uqac.pmm.model.Entrainement
import com.uqac.pmm.model.Exercice


@Database(entities = [Entrainement::class], version = 1)
@TypeConverters(TypeConversion::class)
abstract class EntrainementDataBase: RoomDatabase(){

    abstract fun getEntrainementDao() : EntrainementDao

}

