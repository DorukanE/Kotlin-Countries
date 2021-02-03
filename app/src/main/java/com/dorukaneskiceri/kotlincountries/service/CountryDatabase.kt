package com.dorukaneskiceri.kotlincountries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dorukaneskiceri.kotlincountries.model.Country

//might be arrayOf() or [] choose what you want
@Database(entities = [Country::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDAO

    //Singleton

    companion object {
        @Volatile
        private var instance: CountryDatabase? = null
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) =
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                CountryDatabase::class.java,
                "countrydatabase"
            )
                .build()
    }
}