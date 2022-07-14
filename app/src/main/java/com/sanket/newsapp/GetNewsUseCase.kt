package com.sanket.newsapp

import com.sanket.newsapp.api.Resource
import com.sanket.newsapp.api.models.response.NewsResponse
import com.sanket.newsapp.data.NewsRepository

class GetNewsUseCase(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(isNetworkAvailable: Boolean): Resource<NewsResponse> {
        return if (isNetworkAvailable) {
            val response = newsRepository.getTopNewsFromRemoteSource()
            if (response.isSuccess()) {
                newsRepository.saveTopNewsToLocalSource(response)
            }
            response
        } else {
            newsRepository.getTopNewsFromLocalSource()
        }
    }

}