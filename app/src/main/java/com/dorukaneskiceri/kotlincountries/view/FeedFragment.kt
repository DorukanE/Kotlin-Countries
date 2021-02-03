package com.dorukaneskiceri.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dorukaneskiceri.kotlincountries.R
import com.dorukaneskiceri.kotlincountries.adapter.RecyclerAdapterCountry
import com.dorukaneskiceri.kotlincountries.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {

    private lateinit var feedViewModel: FeedViewModel
    private var countryAdapter = RecyclerAdapterCountry(arrayListOf())
    private var isRefreshing: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        recyclerViewCountries.layoutManager = LinearLayoutManager(view.context)

        feedViewModel.refreshLayout(isRefreshing)
        recyclerViewCountries.adapter = countryAdapter

        swipeRefreshLayout.setOnRefreshListener {
            isRefreshing = true
            feedViewModel.refreshLayout(isRefreshing)
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        feedViewModel.countries.observe(viewLifecycleOwner, {countries ->
            countries?.let {
                recyclerViewCountries.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        feedViewModel.countryError.observe(viewLifecycleOwner, {error ->
            error?.let {
                if(it){
                    recyclerViewCountries.visibility = View.INVISIBLE
                    textViewError.visibility = View.VISIBLE
                }else{
                    textViewError.visibility = View.INVISIBLE
                }
            }
        })

        feedViewModel.countryLoading.observe(viewLifecycleOwner, {loading ->
            loading?.let {
                if(it){
                    countryLoading.visibility = View.VISIBLE
                    textViewError.visibility = View.INVISIBLE
                }else{
                    countryLoading.visibility = View.INVISIBLE
                }
            }
        })
    }
}