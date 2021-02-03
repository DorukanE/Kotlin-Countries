package com.dorukaneskiceri.kotlincountries.service

import com.dorukaneskiceri.kotlincountries.model.Country
import com.dorukaneskiceri.kotlincountries.utils.Utils
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class CountriesService {

    private val api = Retrofit.Builder()
        .baseUrl(Utils().BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CountryAPI::class.java)

    fun getCountriesFromAPI(): Single<List<Country>>{
        return api.getCountriesFromAPI()
    }
}