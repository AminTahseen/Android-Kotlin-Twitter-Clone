package com.example.threadssocialmediaapp.views.loggedIn.post

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
class PostViewModel @Inject constructor() : BaseViewModel<PostEvents>() {

    fun closeActivity() = callEvent(PostEvents.CloseActivity)

    private fun callEvent(events: PostEvents) {
        viewModelScope.launch {
            _events.postValue(events)
        }
    }
}