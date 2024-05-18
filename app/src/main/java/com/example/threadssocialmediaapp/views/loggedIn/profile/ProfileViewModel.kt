package com.example.threadssocialmediaapp.views.loggedIn.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threadssocialmediaapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() :BaseViewModel<ProfileEvents>() {




    private fun callEvent(events: ProfileEvents) {
        viewModelScope.launch {
            _events.postValue(events)
        }
    }
}