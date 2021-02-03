package com.dorukaneskiceri.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dorukaneskiceri.kotlincountries.model.Country

class CountryViewModel: ViewModel() {

    val country = MutableLiveData<Country>()

    fun getCountryDetails(){
        getDataFromRoom()
    }

    private fun getDataFromRoom() {
        val chosenCountry = Country("Turkey", "Ankara", "Europe", "TRY", "www.google.com", "Turkish")
        country.value = chosenCountry
    }

}