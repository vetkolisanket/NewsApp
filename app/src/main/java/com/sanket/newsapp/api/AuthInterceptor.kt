package com.sanket.newsapp.api

import android.util.Log
import com.sanket.newsapp.TAG
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
        val authorisedRequest = builder
            .header(ApiConstants.RestApi.X_API_KEY, ApiConstants.API_KEY)
            .build()

        Log.i(TAG, "intercept: sending request $authorisedRequest")

        return chain.proceed(authorisedRequest)
    }
}