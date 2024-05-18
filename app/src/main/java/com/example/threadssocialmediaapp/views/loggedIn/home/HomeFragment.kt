package com.example.threadssocialmediaapp.views.loggedIn.home

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
import com.example.threadssocialmediaapp.databinding.FragmentHomeBinding
import com.example.threadssocialmediaapp.models.dto.PostDTO
import com.example.threadssocialmediaapp.utils.gone
import com.example.threadssocialmediaapp.utils.visible
import com.example.threadssocialmediaapp.views.dialogs.FullPictureDialog
import com.example.threadssocialmediaapp.views.loggedIn.home.adapters.HomeFeedAdapter
import com.paginate.Paginate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeFeedAdapter: HomeFeedAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment
        homeBinding.viewModel = homeViewModel
        observeEvents()
        setAdapter()
        return homeBinding.root
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            homeViewModel.events.observe(viewLifecycleOwner) {
                when (it) {
                    is HomeEvents.GetPosts -> {
                        homeFeedAdapter.addItems(it.posts)
                        Log.d("postsCountItems", "recycler : ${homeFeedAdapter.itemCount}")
                    }

                    is HomeEvents.ShowFullImageDialog -> {
                        FullPictureDialog(it.imageURL).show(
                            childFragmentManager, "fullPicDialog"
                        )
                    }

                    else -> {

                    }
                }
            }
        }
    }


    private fun setAdapter() {
        homeFeedAdapter = HomeFeedAdapter(
            onImageClick = {
            },
            onPostClick = {
                try {
                    val bundle = Bundle()
                    bundle.putString("postId", it.id)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_postDetailsFragment,
                        bundle
                    )
                } catch (_: Exception) {

                }
            },
        )
        homeBinding.homeFeedRecycler.adapter = homeFeedAdapter
        Paginate.with(homeBinding.homeFeedRecycler, homeViewModel)
            .setLoadingTriggerThreshold(1)
            .addLoadingListItem(true)
            .build()
    }

}