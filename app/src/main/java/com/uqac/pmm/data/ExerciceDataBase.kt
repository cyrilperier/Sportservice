package com.uqac.pmm.data

import androidx.room.*
import androidx.room.Database
import com.uqac.pmm.model.Exercice
import com.uqac.pmm.model.Type
import androidx.room.TypeConverters
import androidx.room.TypeConverter

@Database(entities = [Exercice::class], version = 1)
@TypeConverters(TypeConversion::class)
abstract class ExerciceDataBase: RoomDatabase(){

    abstract fun getExerciceDao() : ExerciceDao

}

class TypeConversion{

    @TypeConverter fun toType(value: String) = Type.valueOf(value)
    @TypeConverter fun toSting(value: Type)= value.name



}