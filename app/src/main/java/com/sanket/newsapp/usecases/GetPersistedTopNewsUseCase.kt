package com.sanket.newsapp.usecases

import com.sanket.newsapp.api.Resource
import com.sanket.newsapp.api.models.response.NewsResponse
import com.sanket.newsapp.data.NewsRepository

class GetPersistedTopNewsUseCase(private val repository: NewsRepository) {

    suspend operator fun invoke(): Resource<NewsResponse> = repository.getTopNewsFromLocalSource()

}