package com.dorukaneskiceri.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.dorukaneskiceri.kotlincountries.R
import com.dorukaneskiceri.kotlincountries.model.Country
import com.dorukaneskiceri.kotlincountries.util.downloadFormUrl
import com.dorukaneskiceri.kotlincountries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.fragment_country.view.*
import kotlinx.android.synthetic.main.recycler_view_countries.view.*

class RecyclerAdapterCountry(private val arrayListCountries: ArrayList<Country>) : RecyclerView.Adapter<RecyclerAdapterCountry.CountryHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_countries, parent, false)
        return CountryHolder(view)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.view.textViewCountry.text = arrayListCountries[position].countryName
        holder.view.textViewContinental.text = arrayListCountries[position].countryContinental
        holder.view.textViewUuid.text = arrayListCountries[position].countryUuid.toString()
        val countryUuid = holder.view.textViewUuid.text.toString().toInt()
        holder.view.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment().setCountryUuid(countryUuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.view.imageView.downloadFormUrl(arrayListCountries[position].countryFlagUrl, holder.view.context)
    }

    override fun getItemCount(): Int {
        return arrayListCountries.size
    }

    class CountryHolder(var view: View): RecyclerView.ViewHolder(view){

    }

    fun updateCountryList(newCountryList: List<Country>){
        arrayListCountries.clear()
        arrayListCountries.addAll(newCountryList)
        notifyDataSetChanged()
    }
}