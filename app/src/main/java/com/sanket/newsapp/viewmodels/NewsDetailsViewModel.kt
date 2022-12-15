package com.sanket.newsapp.viewmodels

import androidx.lifecycle.*
import com.sanket.newsapp.api.Status
import com.sanket.newsapp.data.NewsRepository
import com.sanket.newsapp.data.models.Article
import com.sanket.newsapp.usecases.GetNewsDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsDetailsViewModel(private val repository: NewsRepository): BaseViewModel() {

    private val _articleLD = MutableLiveData<Article>()
    val articleLD: LiveData<Article> = _articleLD

    private val getNewsDetailsUseCase = GetNewsDetailsUseCase(repository)

    fun getNewsDetails(title: String) {
        _loadingLD.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = getNewsDetailsUseCase(title)
            when (response.status) {
                Status.SUCCESS -> _articleLD.postValue(response.data)
                Status.ERROR -> _errorLD.postValue(response.message)
                else -> Unit
            }
            _loadingLD.postValue(false)
        }
    }

}

class NewsDetailsViewModelFactory(private val repository: NewsRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsDetailsViewModel::class.java)) {
            return NewsDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}