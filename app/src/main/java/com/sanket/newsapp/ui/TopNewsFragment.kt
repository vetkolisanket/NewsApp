package com.sanket.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanket.newsapp.MainViewModel
import com.sanket.newsapp.data.models.Article
import com.sanket.newsapp.databinding.FragmentTopNewsBinding

class TopNewsFragment : Fragment() {

    private lateinit var binding: FragmentTopNewsBinding
    private val articles by lazy { mutableListOf<Article>() }
    private val adapter by lazy { NewsAdapter(articles) }
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTopNewsBinding.inflate(layoutInflater)
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
            errorLD.observe(viewLifecycleOwner) { toast(it.getText(requireContext())) }
        }
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = TopNewsFragment()
    }
}