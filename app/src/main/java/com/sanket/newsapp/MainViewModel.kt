package com.sanket.newsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanket.newsapp.api.Resource
import com.sanket.newsapp.api.models.response.NewsResponse
import com.sanket.newsapp.data.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _newsLD = MutableLiveData<Resource<NewsResponse>>()
    val newsLD: LiveData<Resource<NewsResponse>> = _newsLD

    fun getNews(query: String = "latest") {
        _newsLD.value = Resource.loading()
        viewModelScope.launch(Dispatchers.IO) {
            val news = NewsRepository.getNews(query)
            _newsLD.postValue(news)
        }
    }

}