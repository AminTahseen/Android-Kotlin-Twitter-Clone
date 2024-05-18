package com.example.threadssocialmediaapp.views.loggedIn.postDetails

import com.example.threadssocialmediaapp.models.remote.dto.CommentsDTO
import com.example.threadssocialmediaapp.models.remote.dto.PostDTO

sealed class PostDetailsEvents {
    data object OnBackPress : PostDetailsEvents()
    data class OnPostDetails(val post: PostDTO.Post) : PostDetailsEvents()
    data class HideShowProgress(val status: Boolean) : PostDetailsEvents()
    data object NoDataFound : PostDetailsEvents()
    data class GetComments(val comments: List<CommentsDTO.Comment>) : PostDetailsEvents()
    data class NoCommentsFound(val isTrue: Boolean) : PostDetailsEvents()
}