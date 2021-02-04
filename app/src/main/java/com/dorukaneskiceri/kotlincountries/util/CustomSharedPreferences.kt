package com.dorukaneskiceri.kotlincountries.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferences {

    companion object{

        private const val PREFERENCES_TIME: String = "preferences_time"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile private var instance: CustomSharedPreferences? = null
        private val lock = Any()
        operator fun invoke(context: Context): CustomSharedPreferences = instance ?: synchronized(lock){
            instance ?: makeSharedPref(context).also {
                instance = it
            }
        }

        private fun makeSharedPref(context: Context): CustomSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
            return CustomSharedPreferences()
        }
    }

    fun saveTime(time: Long){
        sharedPreferences?.edit(commit = true){
            putLong(PREFERENCES_TIME, time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(PREFERENCES_TIME, 0)
}