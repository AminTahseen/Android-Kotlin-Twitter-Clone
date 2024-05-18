package com.example.threadssocialmediaapp.views.loggedIn.postDetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threadssocialmediaapp.base.BaseViewModel
import com.example.threadssocialmediaapp.models.paginate.PaginateCommentsRequest
import com.example.threadssocialmediaapp.models.useCases.GetCommentsOnPostUseCase
import com.example.threadssocialmediaapp.models.useCases.GetPostByIdUseCase
import com.example.threadssocialmediaapp.utils.calculateTotalPages
import com.example.threadssocialmediaapp.views.loggedIn.home.HomeEvents
import com.paginate.Paginate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val getCommentsOnPostUseCase: GetCommentsOnPostUseCase
) : BaseViewModel<PostDetailsEvents>(), Paginate.Callbacks {

    @JvmField
    var isLoading: Boolean = false
    private var hasLoadedAllItems: Boolean = false
    private var currentPage = 0
    private val limit = 10
    private var postId = ""

    fun onBackPress() = callEvent(PostDetailsEvents.OnBackPress)

    fun getPostById(id: String) {
        postId = id
        callEvent(PostDetailsEvents.HideShowProgress(true))
        viewModelScope.launch(Dispatchers.IO) {
            getPostByIdUseCase(id).catch {
                withContext(Dispatchers.Main) {
                    callEvent(PostDetailsEvents.HideShowProgress(false))
                }
            }.collect {
                withContext(Dispatchers.Main) {
                    if (it == null) {
                        callEvent(PostDetailsEvents.NoDataFound)
                    } else {
                        callEvent(PostDetailsEvents.HideShowProgress(false))
                        Log.d("PostDetailsss", it.toString())
                        callEvent(PostDetailsEvents.OnPostDetails(it))
                    }
                }
            }
        }
    }

    private fun getPostComments(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getCommentsOnPostUseCase(
                PaginateCommentsRequest(
                    page = currentPage,
                    limit = limit,
                    postId = postId
                )
            ).catch {
                withContext(Dispatchers.Main) {
                    isLoading = false
                    hasLoadedAllItems = true
                }
            }.collect {
                isLoading = false
                withContext(Dispatchers.Main) {
                    Log.d("commentsData", it.toString())
                    val items = it?.data
                    if (items?.isEmpty() == true) {
                         callEvent(PostDetailsEvents.NoCommentsFound(true))
                    } else {
                           callEvent(PostDetailsEvents.NoCommentsFound(false))
                        if(it?.total!!<=limit){
                            hasLoadedAllItems=true
                        }else{
                            currentPage = (it?.page ?: 1) + 1
                            val totalPages = calculateTotalPages(limit, it?.total!!)
                            hasLoadedAllItems = (it?.page ?: 1) >= totalPages
                            Log.d("hasLoaded", hasLoadedAllItems.toString())
                        }
                        items?.let { comments ->
                            Log.d("commentsCount", comments.size.toString())
                            callEvent(PostDetailsEvents.GetComments(comments))
                        }
                    }
                }
            }
        }
    }


    private fun callEvent(events: PostDetailsEvents) {
        viewModelScope.launch {
            _events.postValue(events)
        }
    }

    override fun onLoadMore() {
        if (postId.isEmpty().not()) {
            getPostComments(postId)
        } else {
            Log.d("posts", "null...")
        }
    }

    override fun isLoading(): Boolean {
        return isLoading
    }

    override fun hasLoadedAllItems(): Boolean {
        return hasLoadedAllItems
    }
}