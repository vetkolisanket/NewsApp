package com.sanket.newsapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sanket.newsapp.Constants

@Entity(tableName = Constants.DB.TABLE_NAME)
class Article(

    @ColumnInfo(name = Constants.DB.Columns.SOURCE)
    @SerializedName("source")
    val sourceGist: SourceGist,

    @ColumnInfo(name = Constants.DB.Columns.AUTHOR)
    @SerializedName("author")
    val author: String?,

    @PrimaryKey
    @ColumnInfo(name = Constants.DB.Columns.TITLE)
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = Constants.DB.Columns.DESCRIPTION)
    @SerializedName("description")
    val description: String,

    @ColumnInfo(name = Constants.DB.Columns.URL)
    @SerializedName("url")
    val url: String,

    @ColumnInfo(name = Constants.DB.Columns.URL_TO_IMAGE)
    @SerializedName("urlToImage")
    val imageUrl: String,

    @ColumnInfo(name = Constants.DB.Columns.PUBLISHED_AT)
    @SerializedName("publishedAt")
    val publishedAt: String,

    @ColumnInfo(name = Constants.DB.Columns.CONTENT)
    @SerializedName("content")
    val content: String

)