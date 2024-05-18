package com.example.threadssocialmediaapp.views.loggedIn.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threadssocialmediaapp.base.BaseViewModel
import com.example.threadssocialmediaapp.models.local.model.SearchHistoryItem
import com.example.threadssocialmediaapp.models.useCases.AddToSearchHistoryUseCase
import com.example.threadssocialmediaapp.models.useCases.GetRecentSearchHistoryUseCase
import com.example.threadssocialmediaapp.utils.getCurrentDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val addToSearchHistoryUseCase: AddToSearchHistoryUseCase,
    private val getRecentSearchHistoryUseCase: GetRecentSearchHistoryUseCase
) : BaseViewModel<SearchEvents>() {


    fun searchForTags(tag: String) {
        if (tag.isEmpty()) {
            callEvent(SearchEvents.ShowMessage("Search field cannot be empty."))
            callEvent(SearchEvents.None)
        } else {
            addToSearchHistory(tag)
        }
    }

    fun getRecentSearchHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            getRecentSearchHistoryUseCase().catch {
                withContext(Dispatchers.Main) {
                    callEvent(SearchEvents.ShowMessage("An error occurred fetching history"))
                    callEvent(SearchEvents.None)
                }
            }.collect {
                withContext(Dispatchers.Main) {
                    it?.let {
                        Log.d("RECENThistory", it.toString())
                        callEvent(SearchEvents.GetSearchHistoryList(it))
                    }
                }
            }
        }
    }

    private fun addToSearchHistory(tag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentDate = getCurrentDate()
            addToSearchHistoryUseCase(SearchHistoryItem(tag, currentDate, "123"))
                .catch {
                    withContext(Dispatchers.Main) {
                        callEvent(SearchEvents.ShowMessage("An error occurred"))
                        callEvent(SearchEvents.None)
                    }
                }
                .collect {
                    withContext(Dispatchers.Main) {
                        callEvent(SearchEvents.NavigateToSearchList(tag))
                        callEvent(SearchEvents.None)
                    }
                }
        }
    }

    private fun callEvent(events: SearchEvents) {
        viewModelScope.launch {
            _events.value = events
        }
    }
}