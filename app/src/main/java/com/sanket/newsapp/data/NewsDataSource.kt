package com.sanket.newsapp.data

import com.sanket.newsapp.api.models.response.NewsResponse

interface NewsDataSource {

    suspend fun getNews(query: String): NewsResponse?

}