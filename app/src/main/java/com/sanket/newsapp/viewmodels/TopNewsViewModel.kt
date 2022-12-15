package com.sanket.newsapp.viewmodels

import androidx.lifecycle.*
import com.sanket.newsapp.api.Status
import com.sanket.newsapp.api.models.response.NewsResponse
import com.sanket.newsapp.data.NewsRepository
import com.sanket.newsapp.usecases.GetTopNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopNewsViewModel(private val repository: NewsRepository) : BaseViewModel() {

    private val _newsLD = MutableLiveData<NewsResponse>()

    val newsLD: LiveData<NewsResponse> = _newsLD

    val getTopNewsUseCase by lazy { GetTopNewsUseCase(repository) }

    fun getTopHeadlines() {
        _loadingLD.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val news = getTopNewsUseCase(isNetworkAvailable())
            when (news.status) {
                Status.OFFLINE,
                Status.SUCCESS -> _newsLD.postValue(news.data!!)
                Status.ERROR -> _errorLD.postValue(news.message!!)
                else -> Unit
            }
            _loadingLD.postValue(false)
        }
    }

}

class TopNewsViewModelFactory(private val repository: NewsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(TopNewsViewModel::class.java)) {
            return TopNewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}