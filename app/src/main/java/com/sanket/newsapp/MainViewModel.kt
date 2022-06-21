package com.sanket.newsapp

import android.app.Application
import androidx.lifecycle.*
import com.sanket.newsapp.api.Resource
import com.sanket.newsapp.api.models.response.NewsResponse
import com.sanket.newsapp.data.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository by lazy { NewsRepository.getInstance(getApplication<NewsApplication>().database.articlesDao()) }
    private val _newsLD = MutableLiveData<Resource<NewsResponse>>()
    val newsLD: LiveData<Resource<NewsResponse>> = _newsLD

    fun getNews(query: String = "latest") {
        _newsLD.value = Resource.loading()
        viewModelScope.launch(Dispatchers.IO) {
            val news =
                if (getApplication<NewsApplication>().applicationContext.isConnectedToInternet()) {
                    repository.getNewsFromRemoteSource(query)
                } else {
                    repository.getNewsFromLocalSource(query)
                }
            _newsLD.postValue(news)
        }
    }

}