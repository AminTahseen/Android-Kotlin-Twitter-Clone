package com.example.threadssocialmediaapp.views.loggedIn.search.searchList

import com.example.threadssocialmediaapp.models.remote.dto.PostDTO
import com.example.threadssocialmediaapp.views.loggedIn.home.HomeEvents
import com.example.threadssocialmediaapp.views.loggedIn.postDetails.PostDetailsEvents

sealed class SearchListEvents {
    data class GetPosts(val posts: List<PostDTO.Post>) : SearchListEvents()
    data object None : SearchListEvents()
    data object OnBackPress : SearchListEvents()

}