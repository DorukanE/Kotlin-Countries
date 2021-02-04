package com.dorukaneskiceri.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.dorukaneskiceri.kotlincountries.R
import com.dorukaneskiceri.kotlincountries.databinding.RecyclerViewCountriesBinding
import com.dorukaneskiceri.kotlincountries.model.Country
import com.dorukaneskiceri.kotlincountries.util.downloadFormUrl
import com.dorukaneskiceri.kotlincountries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.fragment_country.view.*
import kotlinx.android.synthetic.main.recycler_view_countries.view.*

class RecyclerAdapterCountry(private val arrayListCountries: ArrayList<Country>) : RecyclerView.Adapter<RecyclerAdapterCountry.CountryHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RecyclerViewCountriesBinding>(inflater, R.layout.recycler_view_countries, parent, false)
        return CountryHolder(view)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.view.country = arrayListCountries[position]
    }

    override fun getItemCount(): Int {
        return arrayListCountries.size
    }

    class CountryHolder(var view: RecyclerViewCountriesBinding): RecyclerView.ViewHolder(view.root){

    }

    fun updateCountryList(newCountryList: List<Country>){
        arrayListCountries.clear()
        arrayListCountries.addAll(newCountryList)
        notifyDataSetChanged()
    }
}