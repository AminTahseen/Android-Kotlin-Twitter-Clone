package com.example.threadssocialmediaapp.views.loggedIn.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.threadssocialmediaapp.base.BaseViewModel
import com.example.threadssocialmediaapp.models.paginate.PaginateRequest
import com.example.threadssocialmediaapp.models.useCases.GetPostsUseCase
import com.example.threadssocialmediaapp.utils.calculateTotalPages
import com.paginate.Paginate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : BaseViewModel<HomeEvents>(), Paginate.Callbacks {
    @JvmField
    var isLoading: Boolean = false
    private var hasLoadedAllItems: Boolean = false
    private var currentPage = 0
    private val limit = 10

    init {
        Log.d("viewModell", "called ")
    }

    private fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("currentPage", "page = $currentPage")
            getPostsUseCase(PaginateRequest(currentPage, limit)).catch {
                withContext(Dispatchers.Main) {
                    isLoading = false
                    hasLoadedAllItems = true
                }
            }.collect {
                isLoading = false
                withContext(Dispatchers.Main) {
                    Log.d("postsData", it.toString())
                    val items = it?.data
                    if (it == null) {
                        sendEvent(HomeEvents.None)
                    } else {
                        currentPage = (it.page ?: 1) + 1
                        hasLoadedAllItems =
                            (it.page ?: 1) >= calculateTotalPages(limit, it.total!!)
                        items?.let { posts ->
                            Log.d("postsCount", posts.size.toString())
                            sendEvent(HomeEvents.GetPosts(posts))
                            sendEvent(HomeEvents.None)
                        }
                    }
                }
            }
        }
    }

    fun showImageDialog(imageURL: String) {
        sendEvent(HomeEvents.ShowFullImageDialog(imageURL))
    }


    private fun sendEvent(event: HomeEvents) {
        viewModelScope.launch {
            _events.value = event
        }
    }

    override fun onLoadMore() {
        Log.d("TAG", "onLoadMore: $currentPage")
        getPosts()
    }

    override fun isLoading(): Boolean {
        return isLoading
    }

    override fun hasLoadedAllItems(): Boolean {
        return hasLoadedAllItems
    }
}