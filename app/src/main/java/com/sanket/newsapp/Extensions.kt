package com.sanket.newsapp

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

fun ImageView.load(url: String) {
    GlideApp.with(this.context).load(url).centerCrop()
        .placeholder(R.drawable.ic_launcher_foreground).into(this)
}

fun Context.isConnectedToInternet(): Boolean {
    val netInfo =
        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}