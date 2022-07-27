package com.sanket.newsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sanket.newsapp.api.UiText

abstract class BaseViewModel : ViewModel() {

    protected val _loadingLD = MutableLiveData<Boolean>()
    protected val _errorLD = MutableLiveData<UiText>()

    val loadingLD: LiveData<Boolean> = _loadingLD
    val errorLD: LiveData<UiText> = _errorLD

    val isNetworkAvailable = MutableLiveData<Boolean>()

    protected fun isNetworkAvailable() = isNetworkAvailable.value == true

}