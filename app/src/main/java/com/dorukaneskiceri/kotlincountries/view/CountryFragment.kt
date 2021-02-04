package com.dorukaneskiceri.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.dorukaneskiceri.kotlincountries.R
import com.dorukaneskiceri.kotlincountries.databinding.FragmentCountryBinding
import com.dorukaneskiceri.kotlincountries.util.downloadFormUrl
import com.dorukaneskiceri.kotlincountries.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*

class CountryFragment : Fragment() {

    private var countryUuid = 0
    private lateinit var countryViewModel: CountryViewModel
    private lateinit var dataBinding: FragmentCountryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_country, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        countryViewModel.getCountryDetails(countryUuid)
        observeLiveData()
    }

    private fun observeLiveData(){
        countryViewModel.country.observe(viewLifecycleOwner, {country ->
            country?.let {
                dataBinding.countryDetail = it
            }
        })
    }
}