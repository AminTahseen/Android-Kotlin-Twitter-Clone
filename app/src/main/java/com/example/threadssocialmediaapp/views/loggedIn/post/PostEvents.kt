package com.example.threadssocialmediaapp.views.loggedIn.post

sealed class PostEvents {
    data object CloseActivity : PostEvents()
}