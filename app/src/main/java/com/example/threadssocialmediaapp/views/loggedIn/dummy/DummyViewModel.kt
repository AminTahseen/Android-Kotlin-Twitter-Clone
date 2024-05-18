package com.example.threadssocialmediaapp.views.loggedIn.dummy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threadssocialmediaapp.views.auth.intro.IntroEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor() : ViewModel() {
    private val _events = MutableSharedFlow<DummyEvents>()
    val events = _events.asSharedFlow()



    private fun callEvent(events: DummyEvents) {
        viewModelScope.launch {
            _events.emit(events)
        }
    }
}