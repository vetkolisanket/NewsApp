package com.sanket.newsapp

import androidx.lifecycle.*
import com.sanket.newsapp.api.Status
import com.sanket.newsapp.api.UiText
import com.sanket.newsapp.api.models.response.NewsResponse
import com.sanket.newsapp.data.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchNewsViewModel(private val repository: NewsRepository): ViewModel() {

    private val _newsLD = MutableLiveData<NewsResponse>()
    private val _loadingLD = MutableLiveData<Boolean>()
    private val _errorLD = MutableLiveData<UiText>()

    val newsLD: LiveData<NewsResponse> = _newsLD
    val loadingLD: LiveData<Boolean> = _loadingLD
    val errorLD: LiveData<UiText> = _errorLD

    val isNetworkAvailable = MutableLiveData<Boolean>()
    val searchNewsUseCase by lazy { SearchNewsUseCase(repository) }

    private fun isNetworkAvailable() = isNetworkAvailable.value == true

    fun searchNews(searchText: String) {
        _loadingLD.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val resource = searchNewsUseCase(isNetworkAvailable(), searchText)
            when (resource.status) {
                Status.SUCCESS -> _newsLD.postValue(resource.data!!)
                Status.OFFLINE -> _errorLD.postValue(UiText.noInternetError())
                Status.ERROR -> _errorLD.postValue(resource.message!!)
                else -> Unit
            }
            _loadingLD.postValue(false)
        }
    }

}

class SearchNewsViewModelFactory(private val repository: NewsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(SearchNewsViewModel::class.java)) {
            return SearchNewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}