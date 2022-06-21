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
import com.sanket.newsapp.api.Status
import com.sanket.newsapp.data.models.Article
import com.sanket.newsapp.databinding.FragmentNewsListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [NewsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsListFragment : Fragment() {

    private lateinit var binding: FragmentNewsListBinding
    private val articles by lazy { mutableListOf<Article>() }
    private val adapter by lazy { NewsAdapter(articles) }
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNewsListBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
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
                adapter = this@NewsListFragment.adapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun initLD() {
        viewModel.newsLD.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> binding.progress.isVisible = true
                Status.ERROR -> {
                    binding.progress.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        it.message?.getText(requireContext()),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Status.SUCCESS -> {
                    binding.progress.isVisible = false
                    adapter.addData(it.data?.articles)
                }
                Status.OFFLINE -> {
                    binding.progress.isVisible = false
                    adapter.addData(it.data?.articles)
                    Toast.makeText(requireContext(), "Data loaded from offline", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NewsListFragment.
         */
        @JvmStatic
        fun newInstance() = NewsListFragment()
    }
}