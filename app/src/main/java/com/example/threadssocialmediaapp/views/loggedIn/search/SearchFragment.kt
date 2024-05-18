package com.example.threadssocialmediaapp.views.loggedIn.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.threadssocialmediaapp.R
import com.example.threadssocialmediaapp.databinding.FragmentLikesBinding
import com.example.threadssocialmediaapp.databinding.FragmentSearchBinding
import com.example.threadssocialmediaapp.models.dto.SearchHistoryItem
import com.example.threadssocialmediaapp.views.loggedIn.likes.LikesViewModel
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var searchBinding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        // Inflate the layout for this fragment
        searchBinding.viewModel = searchViewModel
        observeEvents()
        setAdapter()
        return searchBinding.root
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            searchViewModel.events.observe(viewLifecycleOwner) {

            }
        }
    }

    private fun setAdapter() {
        val searchHistory = listOf(
            SearchHistoryItem("Superman", "02/07/2023"),
            SearchHistoryItem("#palestine", "02/01/2022"),
            SearchHistoryItem("#israelcrimes", "05/02/2023"),
            SearchHistoryItem("Mortal Kombat 2", "05/02/2023")

        )
        val searchHistoryAdapter = SearchHistoryAdapter(searchHistory)
        searchBinding.searchHistoryRecycler.adapter = searchHistoryAdapter
    }
}