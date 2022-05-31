package com.sanket.newsapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sanket.newsapp.api.models.Article
import com.sanket.newsapp.databinding.ItemNewsBinding
import com.sanket.newsapp.load

class NewsAdapter(val articles: MutableList<Article>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context))
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = articles.size

    fun addData(articles: List<Article>?) {
        articles?.let {
            val oldSize = this.articles.size
            this.articles.addAll(it)
            notifyItemRangeInserted(oldSize, it.size)
        }
    }

    inner class NewsViewHolder(val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val article = articles[adapterPosition]
            binding.apply {
                ivNews.load(article.imageUrl)
                tvTitle.text = article.title
                tvDescription.text = article.description
                tvSource.text = article.sourceGist.name
                tvAuthor.text = article.author
            }
        }
    }

}