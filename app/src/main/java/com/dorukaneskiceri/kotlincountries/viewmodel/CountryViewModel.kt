package com.dorukaneskiceri.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.dorukaneskiceri.kotlincountries.model.Country
import com.dorukaneskiceri.kotlincountries.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application): BaseViewModel(application){

    val country = MutableLiveData<Country>()

    fun getCountryDetails(countryUuid: Int) {
        getDataFromRoom(countryUuid)
    }

    private fun getDataFromRoom(countryUuid: Int) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val chosenCountry = dao.getCountry(countryUuid)
            country.value= chosenCountry
        }
    }

}