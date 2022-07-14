package com.sanket.newsapp.data

import com.sanket.newsapp.api.RestClient
import com.sanket.newsapp.api.models.response.NewsResponse

object NewsRemoteDataSource : NewsDataSource {

    private val newsService by lazy { RestClient.getNewsService() }

    override suspend fun getTopNews(): NewsResponse {
        return newsService.getNews(mapOf(Pair("country", "in")))
    }

    suspend fun getNewsForQuery(query: String): NewsResponse {
        return newsService.getNewsForQuery(mapOf(Pair("q", query)))
    }
}