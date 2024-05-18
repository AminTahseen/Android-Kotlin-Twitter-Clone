package com.example.threadssocialmediaapp.views.loggedIn.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.threadssocialmediaapp.R
import com.example.threadssocialmediaapp.databinding.FragmentSearchBinding
import com.example.threadssocialmediaapp.utils.clear
import com.example.threadssocialmediaapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var searchBinding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
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
        searchViewModel.getRecentSearchHistory()
        return searchBinding.root
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            searchViewModel.events.observe(viewLifecycleOwner) {
                when (it) {
                    is SearchEvents.ShowMessage -> {
                        activity?.let { it1 ->
                            showToast(
                                it1,
                                layoutInflater,
                                it.message,
                                R.layout.custom_toast_error_message
                            )
                        }
                    }

                    is SearchEvents.NavigateToSearchList -> {
                        navigate(it.tag)
                    }

                    is SearchEvents.GetSearchHistoryList -> {
                        searchHistoryAdapter.addItems(it.list)
                    }

                    else -> {

                    }
                }
            }
        }
        searchBinding.searchField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchedTag = searchBinding.searchField.text.toString()
                searchViewModel.searchForTags(searchedTag)
            }
            true
        }
    }

    private fun navigate(tag: String) {
        try {
            searchBinding.searchField.clear()
            val bundle = Bundle()
            bundle.putString("searchedTag", tag)
            findNavController().navigate(
                R.id.action_searchFragment_to_searchListFragment,
                bundle
            )
        } catch (_: Exception) {

        }
    }

    private fun setAdapter() {
        searchHistoryAdapter = SearchHistoryAdapter {
            navigate(it)
        }
        searchBinding.searchHistoryRecycler.adapter = searchHistoryAdapter
    }

}