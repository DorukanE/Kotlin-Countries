package com.dorukaneskiceri.kotlincountries.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dorukaneskiceri.kotlincountries.R

class Utils {
    val BASE_URL = "https://raw.githubusercontent.com/"
}

fun ImageView.downloadFormUrl(url: String?, context: Context){
    val options = RequestOptions()
        .placeholder(imagePlaceHolder(context))
        .error(R.drawable.ic_launcher_foreground)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

private fun imagePlaceHolder(context: Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter(value = ["android:downloadUrl"])
fun downloadImage(view: ImageView, url: String){
    view.downloadFormUrl(url, view.context)
}