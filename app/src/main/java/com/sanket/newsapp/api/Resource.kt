package com.sanket.newsapp.api

import androidx.annotation.StringRes

data class Resource<out T>(val status: Status, val data: T?, val message: UiText?) {

    companion object{
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String?, data: T? = null): Resource<T> {
            val uiText = message?.let { UiText.DynamicString(it) } ?: UiText.unknownError()
            return Resource(Status.ERROR, data, uiText)
        }

        fun <T> error(@StringRes id: Int, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, UiText.StringResource(id))
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> offline(data: T? = null): Resource<T> {
            return Resource(Status.OFFLINE, data, null)
        }
    }

    fun isSuccess(): Boolean = status == Status.SUCCESS

}