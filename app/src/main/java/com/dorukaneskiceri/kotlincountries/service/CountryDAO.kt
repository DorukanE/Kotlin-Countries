package com.dorukaneskiceri.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dorukaneskiceri.kotlincountries.model.Country

@Dao
interface CountryDAO {

    @Insert
    suspend fun insertAll(varargs: Country): List<Long>

    @Query("SELECT * FROM Country")
    suspend fun getCountries(): List<Country>

    @Query("SELECT * FROM Country WHERE countryUuid = :countryId")
    suspend fun getCountry(countryId: Int): Country
}