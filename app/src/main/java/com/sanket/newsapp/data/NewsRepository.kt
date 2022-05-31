package com.sanket.newsapp.data

import android.util.Log
import com.sanket.newsapp.api.Resource
import com.sanket.newsapp.api.UiText
import com.sanket.newsapp.TAG
import com.sanket.newsapp.api.RestClient
import com.sanket.newsapp.api.models.response.NewsResponse

object NewsRepository {

    suspend fun getNews(query: String): Resource<NewsResponse> {
        val newsService = RestClient.getNewsService()
        return try {
            val response = newsService.getNews(mutableMapOf(Pair("q", query)))
            if (response.success()) {
                Resource.success(response)
            } else Resource.error(response.message)
        } catch (e: Exception) {
            Log.e(TAG, "onCreate: ${e.localizedMessage}")
            Resource.error(e.localizedMessage)
        }
    }

}