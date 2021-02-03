package com.dorukaneskiceri.kotlincountries.service


import com.dorukaneskiceri.kotlincountries.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountriesFromAPI(): Single<List<Country>>
}