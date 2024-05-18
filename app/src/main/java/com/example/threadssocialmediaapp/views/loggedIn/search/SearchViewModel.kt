package com.example.threadssocialmediaapp.views.loggedIn.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threadssocialmediaapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : BaseViewModel<SearchEvents>() {

    private fun callEvent(events: SearchEvents) {
        viewModelScope.launch {
            _events.value = events
        }
    }
}