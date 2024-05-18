package com.example.threadssocialmediaapp.views.loggedIn.search.searchList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.threadssocialmediaapp.R
import com.example.threadssocialmediaapp.databinding.FragmentSearchListBinding
import com.example.threadssocialmediaapp.views.common.FeedAdapter
import com.paginate.Paginate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchListFragment : Fragment() {

    private lateinit var searchListBinding: FragmentSearchListBinding
    private val searchListViewModel: SearchListViewModel by viewModels()
    private lateinit var homeFeedAdapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_list, container, false)
        // Inflate the layout for this fragment
        searchListBinding.viewModel = searchListViewModel
        observeEvents()
        getData()
        setAdapter()
        return searchListBinding.root
    }

    private fun getData() {
        val searchedTag = arguments?.getString("searchedTag")
        searchListBinding.searchField.setText(searchedTag)
        searchedTag?.let {
            searchListViewModel.getPostByTag(it)
        }
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            searchListViewModel.events.observe(viewLifecycleOwner) {
                when (it) {
                    is SearchListEvents.GetPosts -> {
                        val tag = arguments?.getString("searchedTag")
                        Log.d("postsssss", it.posts.size.toString())
                        if (it.posts.isEmpty()) {
                            searchListBinding.textView2.text = "No posts with '#$tag'..."
                        } else {
                            searchListBinding.textView2.text =
                                "Showing ${it.posts.size} posts with '#$tag'..."
                        }
                        homeFeedAdapter.addItems(it.posts)
                    }

                    SearchListEvents.OnBackPress -> {
                        try {
                            findNavController().popBackStack()
                        } catch (_: Exception) {

                        }
                    }

                    else -> {

                    }
                }
            }
        }
    }

    private fun setAdapter() {
        homeFeedAdapter = FeedAdapter(
            onImageClick = {
            },
            onPostClick = {
                try {
                    val bundle = Bundle()
                    bundle.putString("postId", it.id)
                    findNavController().navigate(
                        R.id.action_searchListFragment_to_postDetailsFragment,
                        bundle
                    )
                } catch (_: Exception) {

                }
            },
        )
        searchListBinding.homeFeedRecycler.adapter = homeFeedAdapter
        Paginate.with(searchListBinding.homeFeedRecycler, searchListViewModel)
            .setLoadingTriggerThreshold(1)
            .addLoadingListItem(true)
            .build()
    }
}