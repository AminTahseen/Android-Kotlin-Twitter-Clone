package com.example.threadssocialmediaapp.views.loggedIn.dashboard

import androidx.lifecycle.viewModelScope
import com.example.threadssocialmediaapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : BaseViewModel<DashboardEvents>() {

    fun newPost() = callEvent(DashboardEvents.NewPost)

    private fun callEvent(events: DashboardEvents) {
        viewModelScope.launch {
            _events.postValue(events)
        }
    }
}