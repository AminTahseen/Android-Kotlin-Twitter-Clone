package com.example.threadssocialmediaapp.views.loggedIn.search

import com.example.threadssocialmediaapp.models.local.model.SearchHistoryItem

sealed class SearchEvents {

    data class GetSearchHistoryList(val list: List<SearchHistoryItem>) : SearchEvents()
    data class NavigateToSearchList(val tag: String) : SearchEvents()
    data class ShowMessage(val message: String) : SearchEvents()
    data object None : SearchEvents()
}