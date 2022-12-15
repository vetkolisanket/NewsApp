package com.sanket.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanket.newsapp.*
import com.sanket.newsapp.data.NewsRepository
import com.sanket.newsapp.data.models.Article
import com.sanket.newsapp.databinding.FragmentTopNewsBinding
import com.sanket.newsapp.viewmodels.TopNewsViewModel
import com.sanket.newsapp.viewmodels.TopNewsViewModelFactory

class TopNewsFragment : BaseFragment() {

    private val binding: FragmentTopNewsBinding by lazy { FragmentTopNewsBinding.inflate(layoutInflater) }
    private val articles by lazy { mutableListOf<Article>() }
    private val adapter by lazy { NewsAdapter(articles) }
    private val viewModel: TopNewsViewModel by viewModels {
        TopNewsViewModelFactory(NewsRepository.getInstance((requireActivity().application as NewsApplication).database.articlesDao()))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        adapter.itemClickListener = object : IOnListItemClickListener {
            override fun onListItemClick(
                listItem: Any,
                view: View,
                viewHolder: RecyclerView.ViewHolder,
                position: Int,
                bundle: Bundle?
            ) {
                findNavController().navigate(R.id.newsDetailsActivity, bundle)
            }
        }
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