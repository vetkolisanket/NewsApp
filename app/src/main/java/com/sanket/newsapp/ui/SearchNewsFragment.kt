package com.sanket.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sanket.newsapp.R
import com.sanket.newsapp.databinding.FragmentSearchNewsBinding
import com.sanket.newsapp.textChanges
import kotlinx.coroutines.flow.*

class SearchNewsFragment : Fragment() {

    companion object {
        fun newInstance() = SearchNewsFragment()
    }

    private val binding: FragmentSearchNewsBinding by lazy {
        FragmentSearchNewsBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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