package com.sanket.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.sanket.newsapp.*
import com.sanket.newsapp.data.NewsRepository
import com.sanket.newsapp.data.models.Article
import com.sanket.newsapp.databinding.ActivityNewsDetailsBinding
import com.sanket.newsapp.viewmodels.NewsDetailsViewModel
import com.sanket.newsapp.viewmodels.NewsDetailsViewModelFactory

class NewsDetailsActivity : AppCompatActivity() {

    private val binding: ActivityNewsDetailsBinding by lazy { ActivityNewsDetailsBinding.inflate(layoutInflater) }
    private val title: String? by lazy { intent.getStringExtra(Constants.BUNDLE_KEYS.TITLE) }
    private val viewModel: NewsDetailsViewModel by viewModels {
        NewsDetailsViewModelFactory(NewsRepository.getInstance((application as NewsApplication).database.articlesDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initLD()
        title?.let { viewModel.getNewsDetails(it) }
    }

    private fun initLD() {
        viewModel.apply {
            articleLD.observe(this@NewsDetailsActivity) { initViews(it) }
            loadingLD.observe(this@NewsDetailsActivity) { binding.progress.isVisible = it }
            errorLD.observe(this@NewsDetailsActivity) { showToast(it.getText(this@NewsDetailsActivity)) }
        }
    }

    private fun initViews(article: Article) {
        binding.apply {
            article.imageUrl?.let { ivNews.load(it) }
            tvTitle.text = article.title
            tvDescription.text = article.content
            tvSource.text = article.sourceGist.name
            tvAuthor.text = article.author
        }
    }
}