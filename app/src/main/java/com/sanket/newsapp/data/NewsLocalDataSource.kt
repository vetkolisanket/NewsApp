package com.sanket.newsapp.data

import com.sanket.newsapp.api.Resource
import com.sanket.newsapp.api.models.response.NewsResponse
import com.sanket.newsapp.data.models.Article
import com.sanket.newsapp.data.source.local.ArticlesDao

class NewsLocalDataSource(private val dao: ArticlesDao): NewsDataSource {

    override suspend fun getTopNews(): NewsResponse {
        val articles = dao.getArticles()
        return NewsResponse(articles)
    }

    suspend fun saveNewsResponse(response: NewsResponse) {
        response.articles.forEach {
            dao.saveArticle(it)
        }
    }

    suspend fun getNewsDetails(title: String): Article {
        return dao.getArticle(title)
    }
}