package com.sanket.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanket.newsapp.*
import com.sanket.newsapp.data.NewsRepository
import com.sanket.newsapp.data.models.Article
import com.sanket.newsapp.databinding.FragmentTopNewsBinding
import com.sanket.newsapp.viewmodels.TopNewsViewModel
import com.sanket.newsapp.viewmodels.TopNewsViewModelFactory

class TopNewsFragment : BaseFragment() {

    private lateinit var binding: FragmentTopNewsBinding
    private val articles by lazy { mutableListOf<Article>() }
    private val adapter by lazy { NewsAdapter(articles) }
    private val viewModel: TopNewsViewModel by viewModels {
        TopNewsViewModelFactory(NewsRepository.getInstance((requireActivity().application as NewsApplication).database.articlesDao()))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTopNewsBinding.inflate(layoutInflater)
        connectionLiveData.observe(this) {
            viewModel.isNetworkAvailable.value = it
        }
        viewModel.isNetworkAvailable.value = requireContext().isConnectedToInternet()
        viewModel.getTopHeadlines()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLD()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.apply {
            rvNews.apply {
                adapter = this@TopNewsFragment.adapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun initLD() {
        viewModel.apply {
            newsLD.observe(viewLifecycleOwner) { adapter.addData(it.articles) }
            loadingLD.observe(viewLifecycleOwner) { binding.progress.isVisible = it }
            errorLD.observe(viewLifecycleOwner) { requireContext().showToast(it.getText(requireContext())) }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TopNewsFragment()
    }
}