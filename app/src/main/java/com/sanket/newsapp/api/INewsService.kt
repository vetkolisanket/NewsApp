package com.sanket.newsapp.api

import com.sanket.newsapp.api.models.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface INewsService {

    @GET(ApiConstants.TOP_HEADLINES)
    suspend fun getNews(@QueryMap queryParams: Map<String, String>): NewsResponse

    @GET(ApiConstants.NEWS)
    suspend fun getNewsForQuery(@QueryMap queryParams: Map<String, String>): NewsResponse
}