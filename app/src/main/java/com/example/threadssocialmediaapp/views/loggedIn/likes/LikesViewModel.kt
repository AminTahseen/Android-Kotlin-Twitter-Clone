package com.example.threadssocialmediaapp.views.loggedIn.likes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threadssocialmediaapp.base.BaseViewModel
import com.example.threadssocialmediaapp.views.auth.intro.IntroEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikesViewModel @Inject constructor() : BaseViewModel<LikesEvents>() {


    private fun callEvent(events: LikesEvents) {
        viewModelScope.launch {
            _events.value = events
        }
    }
}