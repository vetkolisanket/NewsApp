package com.sanket.newsapp.data

import com.sanket.newsapp.api.models.response.NewsResponse
import com.sanket.newsapp.data.source.local.ArticlesDao

class NewsLocalDataSource(private val dao: ArticlesDao): NewsDataSource {

    override suspend fun getNews(): NewsResponse {
        val articles = dao.getArticles()
        return NewsResponse(articles)
    }

    suspend fun saveNewsResponse(response: NewsResponse) {
        response.articles.forEach {
            dao.saveArticle(it)
        }
    }
}