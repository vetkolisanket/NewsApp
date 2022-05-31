package com.sanket.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

fun ViewGroup.inflateView(@LayoutRes viewId: Int): View =
    LayoutInflater.from(this.context).inflate(viewId, this, false)