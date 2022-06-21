package com.sanket.newsapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sanket.newsapp.Constants
import com.sanket.newsapp.data.models.Article

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM " + Constants.DB.TABLE_NAME)
    suspend fun getArticles(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(article: Article)

}