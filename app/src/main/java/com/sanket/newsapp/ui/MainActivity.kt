package com.sanket.newsapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sanket.newsapp.*
import com.sanket.newsapp.data.NewsRepository
import com.sanket.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(NewsRepository.getInstance((application as NewsApplication).database.articlesDao()))
    }
    private lateinit var binding: ActivityMainBinding
    private val connectionLiveData by lazy { ConnectionLiveData(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        connectionLiveData.observe(this) {
            viewModel.isNetworkAvailable.value = it
        }
        viewModel.isNetworkAvailable.value = isConnectedToInternet()
        binding.bnvNews.setupWithNavController((supportFragmentManager.findFragmentById(R.id.fcvNews) as NavHostFragment).navController)
        binding.bnvNews.selectedItemId = R.id.topNewsFragment
        viewModel.getTopHeadlines()
    }

}