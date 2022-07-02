package com.sanket.newsapp

import androidx.lifecycle.*
import com.sanket.newsapp.api.Resource
import com.sanket.newsapp.api.Status
import com.sanket.newsapp.api.UiText
import com.sanket.newsapp.api.models.response.NewsResponse
import com.sanket.newsapp.data.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _newsLD = MutableLiveData<NewsResponse>()
    private val _loadingLD = MutableLiveData<Boolean>()
    private val _errorLD = MutableLiveData<UiText>()

    val newsLD: LiveData<NewsResponse> = _newsLD
    val loadingLD: LiveData<Boolean> = _loadingLD
    val errorLD: LiveData<UiText> = _errorLD

    val isNetworkAvailable = MutableLiveData<Boolean>()
    val getNewsUseCase by lazy { GetNewsUseCase(repository) }

    fun getTopHeadlines() {
        _loadingLD.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val news = getNewsUseCase( isNetworkAvailable.value == true)
            when (news.status) {
                Status.OFFLINE,
                Status.SUCCESS -> {
                    _loadingLD.postValue(false)
                    _newsLD.postValue(news.data!!)
                }
                Status.ERROR -> {
                    _loadingLD.postValue(false)
                    _errorLD.postValue(news.message!!)
                }
                else -> Unit
            }
        }
    }

}

class MainViewModelFactory(private val repository: NewsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}