package com.sanket.newsapp

import com.sanket.newsapp.api.Resource
import com.sanket.newsapp.api.models.response.NewsResponse
import com.sanket.newsapp.data.NewsRepository

class SearchNewsUseCase(private val repository: NewsRepository) {

    suspend operator fun invoke(isNetworkAvailable: Boolean, query: String): Resource<NewsResponse> {
        return if (isNetworkAvailable) {
            repository.getNewsForQuery(query)
        } else Resource.offline()
    }

}