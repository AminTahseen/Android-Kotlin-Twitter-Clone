package com.example.threadssocialmediaapp.views.loggedIn.search.searchList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threadssocialmediaapp.base.BaseViewModel
import com.example.threadssocialmediaapp.models.paginate.PaginatePostTagRequest
import com.example.threadssocialmediaapp.models.paginate.PaginateRequest
import com.example.threadssocialmediaapp.models.useCases.GetPostsByTagUseCase
import com.example.threadssocialmediaapp.utils.calculateTotalPages
import com.example.threadssocialmediaapp.views.loggedIn.home.HomeEvents
import com.example.threadssocialmediaapp.views.loggedIn.postDetails.PostDetailsEvents
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
class SearchListViewModel @Inject constructor(
    private val getPostsByTagUseCase: GetPostsByTagUseCase
) : BaseViewModel<SearchListEvents>(), Paginate.Callbacks {
    private var postTag = ""

    @JvmField
    var isLoading: Boolean = false
    private var hasLoadedAllItems: Boolean = false
    private var currentPage = 0
    private val limit = 10

    fun getPostByTag(tag: String) {
        postTag = tag
    }

    private fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("searchedTag", "tag=$postTag")
            getPostsByTagUseCase(PaginatePostTagRequest(currentPage, limit, tag = postTag)).catch {
                withContext(Dispatchers.Main) {
                    isLoading = false
                    hasLoadedAllItems = true
                }
            }.collect {
                isLoading = false
                withContext(Dispatchers.Main) {
                    if (it?.total!! <= limit) {
                        hasLoadedAllItems = true
                    } else {
                        currentPage = (it?.page ?: 1) + 1
                        hasLoadedAllItems =
                            (it?.page ?: 1) >= calculateTotalPages(limit, it?.total!!)
                    }
                    val items = it?.data
                    items?.let { posts ->
                        Log.d("postsWithTag", posts.size.toString())
                        Log.d("postDataIs", posts.toString())
                        callEvent(SearchListEvents.GetPosts(posts))
                        // callEvent(SearchListEvents.None)
                    }
                }
            }
        }
    }
    fun onBackPress() = callEvent(SearchListEvents.OnBackPress)

    private fun callEvent(events: SearchListEvents) {
        viewModelScope.launch {
            _events.postValue(events)
        }
    }

    override fun onLoadMore() {
        Log.d("TAG", "onLoadMore: $currentPage")
        if (postTag.isEmpty().not()) {
            getPosts()
        } else {
            Log.d("postsWithTag", "dont fetch....")
        }
    }

    override fun isLoading(): Boolean {
        return isLoading
    }

    override fun hasLoadedAllItems(): Boolean {
        return hasLoadedAllItems
    }
}