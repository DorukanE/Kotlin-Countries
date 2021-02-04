package com.dorukaneskiceri.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dorukaneskiceri.kotlincountries.model.Country
import com.dorukaneskiceri.kotlincountries.service.CountriesService
import com.dorukaneskiceri.kotlincountries.service.CountryDatabase
import com.dorukaneskiceri.kotlincountries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countriesApiService = CountriesService()
    private val disposable = CompositeDisposable()
    private val customSharedPref = CustomSharedPreferences(getApplication())
    private val refreshTime = 2 * 60 * 1000 * 1000 * 1000L
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshLayout(isRefreshing: Boolean) {
        val updateTime = customSharedPref.getTime()
        if(updateTime != null && updateTime != 0L){
            if(System.nanoTime() - updateTime < refreshTime){
                getDataFromRoom()
            }else{
                getDataFromAPI(isRefreshing)
            }
        }else{
            getDataFromAPI(isRefreshing)
        }
    }

    fun refreshFromApi(isRefreshing: Boolean){
        getDataFromAPI(isRefreshing)
    }

    private fun getDataFromRoom() {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao().getCountries()
            showCountries(dao)
//            Toast.makeText(getApplication(), "Countries From Room", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromAPI(isRefreshing: Boolean) {
        if (!isRefreshing) {
            countryLoading.value = true
        }

        disposable.add(
            countriesApiService.getCountriesFromAPI()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        storeInRoom(t)
//                        Toast.makeText(getApplication(), "Countries From API", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        println(e.printStackTrace())
                    }

                })
        )
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryLoading.value = false
    }

    private fun storeInRoom(countryList: List<Country>) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteCountries()
            val longList = dao.insertAll(*countryList.toTypedArray())
            var i = 0
            while (i < longList.size){
                countryList[i].countryUuid = longList[i].toInt()
                i++
            }

            showCountries(countryList)
        }
        customSharedPref.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}