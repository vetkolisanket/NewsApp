package com.sanket.newsapp.usecases

import android.icu.text.CaseMap.Title
import com.sanket.newsapp.api.Resource
import com.sanket.newsapp.data.NewsRepository
import com.sanket.newsapp.data.models.Article

class GetNewsDetailsUseCase(private val repository: NewsRepository) {

    suspend operator fun invoke(title: String): Resource<Article> {
        return repository.getNewsDetails(title)
    }

}