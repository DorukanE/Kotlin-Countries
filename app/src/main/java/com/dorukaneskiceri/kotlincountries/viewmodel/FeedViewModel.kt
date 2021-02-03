package com.dorukaneskiceri.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dorukaneskiceri.kotlincountries.model.Country
import com.dorukaneskiceri.kotlincountries.service.CountriesService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FeedViewModel : ViewModel() {

    private val countriesApiService = CountriesService()
    private val disposable = CompositeDisposable()
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshLayout(isRefreshing: Boolean) {
        getDataFromAPI(isRefreshing)
    }

    private fun getDataFromAPI(isRefreshing: Boolean) {
        if(!isRefreshing){
            countryLoading.value = true
        }

        disposable.add(
            countriesApiService.getCountriesFromAPI()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        countries.value = t
                        countryLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        println(e.printStackTrace())
                    }

                })
        )
    }
}