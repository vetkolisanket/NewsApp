package com.sanket.newsapp

import android.app.Application
import com.sanket.newsapp.data.source.local.NewsDatabase

class NewsApplication: Application() {

    val database by lazy { NewsDatabase.getDatabase(this) }

}