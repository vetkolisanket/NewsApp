package com.sanket.newsapp.data

import com.sanket.newsapp.api.RestClient
import com.sanket.newsapp.api.models.response.NewsResponse

object NewsRemoteDataSource: NewsDataSource {
    override suspend fun getNews(query: String): NewsResponse {
        val newsService = RestClient.getNewsService()
        return newsService.getNews(mutableMapOf(Pair("q", query)))
    }
}