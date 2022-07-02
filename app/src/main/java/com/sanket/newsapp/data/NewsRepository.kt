package com.sanket.newsapp.data

import android.util.Log
import com.sanket.newsapp.TAG
import com.sanket.newsapp.api.Resource
import com.sanket.newsapp.api.models.response.NewsResponse
import com.sanket.newsapp.data.source.local.ArticlesDao

class NewsRepository private constructor(dao: ArticlesDao){

    companion object {
        private var INSTANCE: NewsRepository? = null

        fun getInstance(dao: ArticlesDao): NewsRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = NewsRepository(dao)
                INSTANCE = instance
                instance
            }
        }
    }

    private val newsRemoteDataSource: NewsRemoteDataSource by lazy { NewsRemoteDataSource }
    private val newsLocalDataSource: NewsLocalDataSource by lazy { NewsLocalDataSource(dao) }

    suspend fun getNewsFromRemoteSource(query: String): Resource<NewsResponse> {
        return try {
            val response = newsRemoteDataSource.getNews(query)
            if (response.success()) {
                Resource.success(response)
            } else Resource.error(response.message)
        } catch (e: Exception) {
            Log.e(TAG, "getNews: ${e.localizedMessage}")
            Resource.error(e.localizedMessage)
        }
    }

    suspend fun getNewsFromLocalSource(query: String): Resource<NewsResponse> {
        return Resource.offline(newsLocalDataSource.getNews(query))
    }

    suspend fun saveResponse(response: Resource<NewsResponse>) {
        newsLocalDataSource.saveNewsResponse(response.data!!)
    }

}