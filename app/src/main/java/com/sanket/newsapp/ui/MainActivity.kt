package com.sanket.newsapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.sanket.newsapp.MainViewModel
import com.sanket.newsapp.R
import com.sanket.newsapp.api.Status
import com.sanket.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLD()
        viewModel.getNews()
    }

    private fun initLD() {
        viewModel.newsLD.observe(this) {
            when (it.status) {
                Status.LOADING -> binding.progress.isVisible = true
                Status.ERROR -> {
                    binding.progress.isVisible = false
                    Toast.makeText(this, it.message?.getText(this), Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    binding.progress.isVisible = false
                    Toast.makeText(this, it.data.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}