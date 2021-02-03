package com.dorukaneskiceri.kotlincountries.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val countryName: String?,
    @SerializedName("capital")
    val countryCapital: String?,
    @SerializedName("region")
    val countryContinental: String?,
    @SerializedName("currency")
    val countryCurrency: String?,
    @SerializedName("flag")
    val countryFlagUrl: String?,
    @SerializedName("language")
    val countryLanguage: String?)