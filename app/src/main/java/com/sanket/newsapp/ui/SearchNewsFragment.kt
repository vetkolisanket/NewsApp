package com.sanket.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanket.newsapp.*
import com.sanket.newsapp.data.NewsRepository
import com.sanket.newsapp.data.models.Article
import com.sanket.newsapp.databinding.FragmentSearchNewsBinding
import com.sanket.newsapp.viewmodels.SearchNewsViewModel
import com.sanket.newsapp.viewmodels.SearchNewsViewModelFactory
import kotlinx.coroutines.flow.*

class SearchNewsFragment : BaseFragment() {

    private val viewModel: SearchNewsViewModel by viewModels {
        SearchNewsViewModelFactory(NewsRepository.getInstance((requireActivity().application as NewsApplication).database.articlesDao()))
    }
    private val articles by lazy { mutableListOf<Article>() }
    private val adapter by lazy { NewsAdapter(articles) }

    companion object {
        fun newInstance() = SearchNewsFragment()
    }

    private val binding: FragmentSearchNewsBinding by lazy {
        FragmentSearchNewsBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData.observe(this) {
            viewModel.isNetworkAvailable.value = it
        }
        viewModel.isNetworkAvailable.value = requireContext().isConnectedToInternet()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLD()
        initEditText()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.apply {
            rvNews.apply {
                adapter = this@SearchNewsFragment.adapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun initEditText() {
//        initAutoSearchAfterDebounceTime()
        binding.etSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchText = binding.etSearch.text.toString().trim()
                if (searchText.isNotBlank()) {
                    viewModel.searchNews(searchText)
                }
            }
            actionId == EditorInfo.IME_ACTION_SEARCH
        }
    }

    private fun initLD() {
        viewModel.apply {
            newsLD.observe(viewLifecycleOwner) { adapter.addData(it.articles) }
            loadingLD.observe(viewLifecycleOwner) { binding.progress.isVisible = it }
            errorLD.observe(viewLifecycleOwner) {
                requireContext().showToast(
                    it.getText(
                        requireContext()
                    )
                )
            }
        }
    }

    private fun initAutoSearchAfterDebounceTime() {
        binding.etSearch
            .textChanges()
            .filter { it?.length ?: 0 > 2 }
            .debounce(500)
//            .distinctUntilChanged()
            .onEach {
                it?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
            }
            .launchIn(lifecycleScope)
    }

}