package com.sanket.newsapp.viewmodels

import androidx.lifecycle.*
import com.sanket.newsapp.api.Resource
import com.sanket.newsapp.api.Status
import com.sanket.newsapp.api.models.response.NewsResponse
import com.sanket.newsapp.data.NewsRepository
import com.sanket.newsapp.usecases.GetPersistedTopNewsUseCase
import com.sanket.newsapp.usecases.GetTopNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopNewsViewModel(private val repository: NewsRepository) : BaseViewModel() {

    private val _newsLD = MutableLiveData<NewsResponse>()
    private val _resetNewsLD = MutableLiveData<Boolean>()

    val newsLD: LiveData<NewsResponse> = _newsLD
    val resetLD: LiveData<Boolean> = _resetNewsLD

    val getTopNewsUseCase by lazy { GetTopNewsUseCase(repository) }
    val getPersistedTopNewsUseCase by lazy { GetPersistedTopNewsUseCase(repository) }

    fun getTopHeadlines() {
        _loadingLD.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val localNews = getPersistedTopNewsUseCase()
            notifyObservers(localNews)
            val news = getTopNewsUseCase(isNetworkAvailable())
            notifyObservers(news)
            _loadingLD.postValue(false)
        }
    }

    private fun notifyObservers(news: Resource<NewsResponse>) {
        when (news.status) {
            Status.OFFLINE -> _newsLD.postValue(news.data)
            Status.SUCCESS -> {
                _resetNewsLD.postValue(true)
                _newsLD.postValue(news.data!!)
            }
            Status.ERROR -> _errorLD.postValue(news.message!!)
            else -> Unit
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