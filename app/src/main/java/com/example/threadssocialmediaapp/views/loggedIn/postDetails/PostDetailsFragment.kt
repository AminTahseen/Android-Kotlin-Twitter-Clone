package com.example.threadssocialmediaapp.views.loggedIn.postDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.threadssocialmediaapp.R
import com.example.threadssocialmediaapp.databinding.FragmentPostDetailsBinding
import com.example.threadssocialmediaapp.models.dto.PostDTO
import com.example.threadssocialmediaapp.utils.formatDateTime
import com.example.threadssocialmediaapp.utils.gone
import com.example.threadssocialmediaapp.utils.visible
import com.example.threadssocialmediaapp.views.loggedIn.home.adapters.TagsAdapter
import com.example.threadssocialmediaapp.views.loggedIn.postDetails.adapter.CommentsAdapter
import com.paginate.Paginate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {
    private lateinit var postDetailsBinding: FragmentPostDetailsBinding
    private val postDetailsViewModel: PostDetailsViewModel by viewModels()
    private lateinit var commentsAdapter: CommentsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        postDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_post_details, container, false)
        // Inflate the layout for this fragment
        postDetailsBinding.viewModel = postDetailsViewModel
        observeEvents()
        getData()
        setCommentsAdapter()
        return postDetailsBinding.root
    }

    private fun getData() {
        val postId = arguments?.getString("postId")
        postId?.let {
            postDetailsViewModel.getPostById(it)
        }
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            postDetailsViewModel.events.observe(viewLifecycleOwner) {
                when (it) {
                    PostDetailsEvents.OnBackPress -> {
                        try {
                            findNavController().popBackStack()
                        } catch (_: Exception) {

                        }
                    }

                    is PostDetailsEvents.OnPostDetails -> {
                        Log.d("Status", "set details...")
                        setPostDetails(it.post)
                    }

                    is PostDetailsEvents.HideShowProgress -> {
                        Log.d("Status", "hide")
                        postDetailsBinding.progressBar.visible()
                        postDetailsBinding.postDetailsSection.gone()
                        postDetailsBinding.noDataFound.gone()
                    }

                    PostDetailsEvents.NoDataFound -> {
                        postDetailsBinding.progressBar.gone()
                        postDetailsBinding.postDetailsSection.gone()
                        postDetailsBinding.noDataFound.visible()
                    }

                    is PostDetailsEvents.GetComments -> {
                        Log.d("totalComments", it.comments.size.toString())
                        postDetailsBinding.commentsTotal.text="${it.comments.size} Comments"
                        commentsAdapter.addItems(it.comments)
                    }

                    is PostDetailsEvents.NoCommentsFound -> {
                        if (it.isTrue) {
                            postDetailsBinding.commentNotFound.visible()
                            postDetailsBinding.commentsRecycler.gone()
                        } else {
                            postDetailsBinding.commentNotFound.gone()
                            postDetailsBinding.commentsRecycler.visible()
                        }
                    }

                    else -> {}
                }

            }
        }
    }

    private fun setPostDetails(post: PostDTO.Post) {
        postDetailsBinding.progressBar.gone()
        postDetailsBinding.noDataFound.gone()
        postDetailsBinding.postDetailsSection.visible()
        val circularProgressDrawable = CircularProgressDrawable(postDetailsBinding.root.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        val fullName = "${post.owner?.firstName} ${post.owner?.lastName}"
        postDetailsBinding.userName.text = fullName
        postDetailsBinding.postDescription.text = post.text
        if (post.image?.isEmpty() == true) {
            postDetailsBinding.thumbnailCard.gone()
        } else {
            postDetailsBinding.thumbnailCard.visible()
            Glide.with(postDetailsBinding.root).load(post.image)
                .placeholder(circularProgressDrawable)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(postDetailsBinding.postImage)
        }
        Glide.with(postDetailsBinding.root).load(post.owner?.picture)
            .placeholder(circularProgressDrawable)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(postDetailsBinding.profileImage)

        postDetailsBinding.likesTotal.text = "${post.likes} Likes"
        postDetailsBinding.timePosted.text = formatDateTime(post.publishDate!!)
        val tagsAdapter = post.tags?.let { TagsAdapter(tagsList = it) }
        postDetailsBinding.tagsRecycler.adapter = tagsAdapter

    }

    private fun setCommentsAdapter() {
        commentsAdapter = CommentsAdapter()
        postDetailsBinding.commentsRecycler.adapter = commentsAdapter
        Paginate.with(postDetailsBinding.commentsRecycler, postDetailsViewModel)
            .setLoadingTriggerThreshold(1)
            .addLoadingListItem(true)
            .build()
    }
}