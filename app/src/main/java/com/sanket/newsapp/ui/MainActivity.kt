package com.sanket.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.sanket.newsapp.R
import com.sanket.newsapp.TAG
import com.sanket.newsapp.api.ApiConstants
import com.sanket.newsapp.api.AuthInterceptor
import com.sanket.newsapp.api.INewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch(Dispatchers.IO) {
            val httpClient = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
            val newsService = retrofit.create(INewsService::class.java)
            try {
                val response = newsService.getNews(mutableMapOf(Pair("q", "bitcoin")))
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "response.status + response.message", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e(TAG, "onCreate: ${e.localizedMessage}")
            }


        }
    }
}