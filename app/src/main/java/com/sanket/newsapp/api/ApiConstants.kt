package com.sanket.newsapp.api

import com.sanket.newsapp.BuildConfig

object ApiConstants {

    const val BASE_URL = "https://newsapi.org"
    const val API_KEY = BuildConfig.API_KEY

    object RestApi {
        const val API_VERSION = "/v2"
        const val X_API_KEY = "X-Api-Key"

        object Endpoints {
            const val EVERYTHING = "/everything"
        }
    }

    const val GET_NEWS = RestApi.API_VERSION + RestApi.Endpoints.EVERYTHING

}