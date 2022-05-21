package com.sanket.newsapp.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Article (

    @SerializedName("source")
    val sourceGist: SourceGist,

    @SerializedName("author")
    val author: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("urlToImage")
    val imageUrl: String,

    @SerializedName("publishedAt")
    val publishedAt: String,

    @SerializedName("content")
    val content: String

) : Parcelable