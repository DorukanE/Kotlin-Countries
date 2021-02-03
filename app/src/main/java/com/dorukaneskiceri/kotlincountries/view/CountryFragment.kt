package com.dorukaneskiceri.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.dorukaneskiceri.kotlincountries.R
import com.dorukaneskiceri.kotlincountries.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*

class CountryFragment : Fragment() {

    private var countryUuid = 0
    private lateinit var countryViewModel: CountryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
            println(countryUuid)
        }

        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        countryViewModel.getCountryDetails()
        observeLiveData()
    }

    private fun observeLiveData(){
        countryViewModel.country.observe(viewLifecycleOwner, {country ->
            country?.let {
                textViewCountryName.text = it.countryName
                textViewCountryCapital.text = it.countryCapital
                textViewCountryContinental.text = it.countryContinental
                textViewCountryCurrency.text = it.countryCurrency
                textViewCountryLanguage.text = it.countryLanguage
            }
        })
    }
}