package com.sanket.newsapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sanket.newsapp.*
import com.sanket.newsapp.data.NewsRepository
import com.sanket.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.bnvNews.setupWithNavController((supportFragmentManager.findFragmentById(R.id.fcvNews) as NavHostFragment).navController)
        binding.bnvNews.selectedItemId = R.id.topNewsFragment
    }

}