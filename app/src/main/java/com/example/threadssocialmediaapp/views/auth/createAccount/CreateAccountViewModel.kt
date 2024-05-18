package com.example.threadssocialmediaapp.views.auth.createAccount

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
class CreateAccountViewModel @Inject constructor() : BaseViewModel<CreateAccountEvents>() {

    fun onBackPress() {
        callEvent(CreateAccountEvents.OnBackPress)
    }

    private fun callEvent(events: CreateAccountEvents) {
        viewModelScope.launch {
            _events.value = events
        }
    }
}