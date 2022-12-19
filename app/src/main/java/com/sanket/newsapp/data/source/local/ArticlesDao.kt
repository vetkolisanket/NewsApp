package com.sanket.newsapp.data.source.local

import android.icu.text.CaseMap.Title
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sanket.newsapp.Constants
import com.sanket.newsapp.data.models.Article

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM ${Constants.DB.TABLE_NAME}")
    suspend fun getArticles(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(article: Article)

    @Query("SELECT * FROM ${Constants.DB.TABLE_NAME} WHERE ${Constants.DB.Columns.TITLE} = :title")
    suspend fun getArticle(title: String): Article

}