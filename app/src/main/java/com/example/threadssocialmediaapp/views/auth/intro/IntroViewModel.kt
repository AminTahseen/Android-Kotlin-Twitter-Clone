package com.example.threadssocialmediaapp.views.auth.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threadssocialmediaapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor() : BaseViewModel<IntroEvents>() {


    fun navigateToCreateAccount() {
        callEvent(IntroEvents.NavigateToCreateAccount)
    }

    fun navigateToDashboard() {
        callEvent(IntroEvents.NavigateToDashboard)
    }

    private fun callEvent(events: IntroEvents) {
        viewModelScope.launch {
            _events.value = events
            _events.value = IntroEvents.None
        }
    }
}