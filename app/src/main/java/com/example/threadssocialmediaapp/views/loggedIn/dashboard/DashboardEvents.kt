package com.example.threadssocialmediaapp.views.loggedIn.dashboard

sealed class DashboardEvents {
    data object NewPost : DashboardEvents()
}