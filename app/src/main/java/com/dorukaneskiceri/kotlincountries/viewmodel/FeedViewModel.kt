package com.dorukaneskiceri.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dorukaneskiceri.kotlincountries.model.Country

class FeedViewModel : ViewModel() {

     val countries = MutableLiveData<List<Country>>()
     val countryError = MutableLiveData<Boolean>()
     val countryLoading = MutableLiveData<Boolean>()

    fun refreshLayout() {
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        val country = Country("Turkey",
            "Ankara",
            "Europe",
            "TRY",
            "www.google.com",
            "Turkish")

        val country2 = Country("France",
            "Ankara",
            "Europe",
            "TRY",
            "www.google.com",
            "Turkish")

        val country3 = Country("Germany",
            "Ankara",
            "Europe",
            "TRY",
            "www.google.com",
            "Turkish")

        val countryList = arrayListOf(country, country2, country3)
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false

    }
}