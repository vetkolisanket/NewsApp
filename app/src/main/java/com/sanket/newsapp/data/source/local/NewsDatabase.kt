package com.sanket.newsapp.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sanket.newsapp.Constants
import com.sanket.newsapp.data.DataConverter
import com.sanket.newsapp.data.models.Article

/**
 * The Room Database that contains the Articles table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    Constants.DB.NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}