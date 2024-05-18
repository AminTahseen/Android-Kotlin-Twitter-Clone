package com.example.threadssocialmediaapp.views.loggedIn.home

import com.example.threadssocialmediaapp.models.dto.PostDTO

sealed class HomeEvents {
    data class GetPosts(val posts: List<PostDTO.Post>) : HomeEvents()
    data class ShowFullImageDialog(val imageURL: String) : HomeEvents()
    data object None : HomeEvents()
}