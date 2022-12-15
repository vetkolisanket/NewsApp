package com.sanket.newsapp

object Constants {
    object DB {
        const val NAME: String = "News.db"
        const val TABLE_NAME = "Article"

        object Columns {
            const val TITLE = "title"
            const val SOURCE = "source"
            const val AUTHOR = "author"
            const val DESCRIPTION = "description"
            const val URL = "url"
            const val URL_TO_IMAGE = "urlToImage"
            const val PUBLISHED_AT = "publishedAt"
            const val CONTENT = "content"
        }
    }

    object BUNDLE_KEYS {
        const val TITLE: String = "title"
    }
}