package com.sanket.newsapp.api.models.response

import com.google.gson.annotations.SerializedName
import com.sanket.newsapp.api.models.Article

data class NewsResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("code")
    val code: String?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("totalResults")
    val totalResults: Int,

    @SerializedName("articles")
    val articles: List<Article>
) {
    fun success(): Boolean = "ok".equals(status, true)
}